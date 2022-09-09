## TODO

- extract dir structure creation to a separate task, i.e. not `apply`
- ~~use `apply from` in `settings.gradle` instead of polluting it~~
- apply common plugins to all subprojects:

```bash
subprojects {
    apply plugin: "org.jlleitschuh.gradle.ktlint"
    apply plugin: 'kotlin-kapt'
```

- ~~create `api:common` subproject~~
    - ~~make name configurable~~
    - fail if already exists a subproject with the default name
    - make it work for both `groovy` and `kts`
- ~~create `api:live` subproject~~
- ~~create `api:mock` subproject~~
- ~~apply project-wide plugins~~
- ~~configure `plugins.withId('com.android.application') { }`~~
- ~~configure `plugins.withId('com.android.library') { }`~~
- ~~configure `plugins.withId('java-library') { }`~~
- ~~link unit tests in `gradle.projectsEvaluated { }`~~
- link UI tests
- figure out what to do with `id 'org.jetbrains.kotlin.jvm'` plugin requiring an explicit version
  during tests
- also find a way to apply `"org.jetbrains.kotlin.android"` and have project evaluation test pass 
- explain in tips how to have a database module that uses Room (an `android-library`) and still have
  a plain `java-library` Repository layer (Hilt's `SingletonComponent` to the rescue)
- organize all task names as an iterable to automatically run certain tests on all of them
- ~~provide a way to declare feature definitions with automatic layers' creation~~
- ~~make `api` a regular layer for features~~
- check how feature names with forward slashes behave
- allow to customize layers (could be not worth it). At least maybe give users ability to disable
  some predefined layers
- define `includeIfNeeded()` for `orderly.gradle` to choose `mock` or `live` dependencies
- skip task registering if a matching subproject already exists
- provide a way to run only feature subprojects' creation (should be a common case when a new screen
  is added). Possibly can also create these during configuration, or also provide a task to delete
  certain ones.
- make `mock` and `live` names configurable
- fix table below
- add `screen-compose` layer. Or, better, allow to easily add compose dependencies to a layer.
- ~~make subproject creation tasks incremental, i.e. declare outputs and provide a way for Gradle to
  skip creation of already existing subprojects (see `plugin links all subprojects together` test)~~
- figure out a way to describe `include project` lines in `orderly.gradle` as a task output.
- make all `build.gradle` templates swappable by user
- allow users to declare their own repos
- figure out how to allow users to use `DependencyHandler` for `dependenciesApiCommonLive`.
  Currently `api` dependency declaration is not present in the root `build.gradle.kts` since it is
  not a `java-library`. Also, it looks like `org.gradle.kotlin.dsl.DependencyHandlerExtensions` is
  not on the classpath for the buildscript, which is a bit strange (and it does not force a project
  to be a `java-library` it seems).
- extract string literals from everywhere
- solve the mystery of `java-library` not being applied to projects (worked around by
  adding `it.plugins.apply("org.jetbrains.kotlin.jvm")` everywhere dependencies are declared).
  Probably they are being overridden by application of `plugins` extension property to all
  subprojects. UPD: possibly, `it.plugins.apply()` is the one re-writing default plugins.
- apply `dagger.hilt.android.plugin` by default
- migrate to `settings.gradle.kts` and `orderly.gradle.kts`

## Bugs

- ~~`settings.gradle` gets overrun by `orderly.gradle` lines~~

## What it does

**orderly** can be used to bootstrap a modularized Android App, that follows MVVM + Clean
Architecture, i.e. in the end you get an opinionated project structure. The benefit is that you as a
user will be able to skip the setup of relations between the app layers (represented by Gradle
subprojects), and just proceed to implement app features. Consider this to be a rough equivalent of
what Hilt does with its component hierarchy.

## Usage

- apply and configure the plugin via `orderly` extension
- invoke `createAllSubprojects` task to create all layers
- sync the new configuration and enjoy development

## Subprojects' Description

name|type|what has -|-|-
:api:common|java-library|reusable external API code, e.g. `Retrofit` dependency
:api:mock|java-library|mocked external API answers
:api:live|java-library|actual external API client code
:domain|java-library|data classes used by business logic, e.g. in `UseCase` layers
:dto|java-library|data classes expected from external APIs
:common:kotlin|java-library|reusable plain Kotlin code
:common:android|android-library|reusable Android code

## Tips

- list properties as of now should be extended in the following manner to not lose default values:

```
orderly {
  plugins.set(plugins.get() + "org.jlleitschuh.gradle.ktlint")
}
```

## Plugin Development Tips

- `plugin links all subprojects` test is nice for checking subprojects' visibility
- functional tests are much more reliable when `assemble` tasks are invoked as opposed to
  parsing `dependencies` output, which will not fail if a dependency could not be resolved.
  Also, `--dry-run` will not detect a circular dependency.
- use this to publish the plugin locally:

```bash
./gradlew plugin:publishToMavenLocal
```

- add `--stacktrace` to runner arguments if a test fails without obvious error message
