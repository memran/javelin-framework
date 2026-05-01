# Graph Report - javelin-framework  (2026-05-01)

## Corpus Check
- 137 files · ~24,765 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 996 nodes · 2430 edges · 32 communities detected
- Extraction: 53% EXTRACTED · 47% INFERRED · 0% AMBIGUOUS · INFERRED: 1147 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- [[_COMMUNITY_Community 0|Community 0]]
- [[_COMMUNITY_Community 1|Community 1]]
- [[_COMMUNITY_Community 2|Community 2]]
- [[_COMMUNITY_Community 3|Community 3]]
- [[_COMMUNITY_Community 4|Community 4]]
- [[_COMMUNITY_Community 5|Community 5]]
- [[_COMMUNITY_Community 6|Community 6]]
- [[_COMMUNITY_Community 7|Community 7]]
- [[_COMMUNITY_Community 8|Community 8]]
- [[_COMMUNITY_Community 9|Community 9]]
- [[_COMMUNITY_Community 10|Community 10]]
- [[_COMMUNITY_Community 11|Community 11]]
- [[_COMMUNITY_Community 12|Community 12]]
- [[_COMMUNITY_Community 13|Community 13]]
- [[_COMMUNITY_Community 14|Community 14]]
- [[_COMMUNITY_Community 15|Community 15]]
- [[_COMMUNITY_Community 16|Community 16]]
- [[_COMMUNITY_Community 17|Community 17]]
- [[_COMMUNITY_Community 18|Community 18]]
- [[_COMMUNITY_Community 19|Community 19]]
- [[_COMMUNITY_Community 20|Community 20]]
- [[_COMMUNITY_Community 21|Community 21]]
- [[_COMMUNITY_Community 22|Community 22]]
- [[_COMMUNITY_Community 23|Community 23]]
- [[_COMMUNITY_Community 24|Community 24]]
- [[_COMMUNITY_Community 25|Community 25]]
- [[_COMMUNITY_Community 26|Community 26]]
- [[_COMMUNITY_Community 27|Community 27]]
- [[_COMMUNITY_Community 28|Community 28]]
- [[_COMMUNITY_Community 29|Community 29]]
- [[_COMMUNITY_Community 30|Community 30]]
- [[_COMMUNITY_Community 31|Community 31]]

## God Nodes (most connected - your core abstractions)
1. `Collection` - 54 edges
2. `Model` - 37 edges
3. `Validation` - 19 edges
4. `Router` - 18 edges
5. `Input` - 18 edges
6. `Response` - 17 edges
7. `JdbcQueryBuilder` - 17 edges
8. `SupportUtilitiesTest` - 17 edges
9. `Request` - 16 edges
10. `Route` - 15 edges

## Surprising Connections (you probably didn't know these)
- `YamlConfig` --implements--> `Config`  [EXTRACTED]
  modules\javelin-config\src\main\java\io\javelin\config\YamlConfig.java →   _Bridges community 10 → community 2_
- `RecordingDatabase` --implements--> `Database`  [EXTRACTED]
  modules\javelin-core\src\test\java\io\javelin\core\ModelTest.java →   _Bridges community 13 → community 11_

## Communities

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (10): CommandGenerationTest, ProjectLauncher, GeneratorSupport, Main, FileGenerator, Names, PebbleViewRendererTest, ProcessTasks (+2 more)

### Community 1 - "Community 1"
Cohesion: 0.06
Nodes (6): ModelTest, Arr, Collection, Obj, SupportUtilitiesTest, ValidationRuleRegistry

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (25): CacheServiceProvider, Config, ConfigServiceProvider, YamlConfigTest, ConsoleServiceProvider, JavelinConsole, ConsoleKernel, Config (+17 more)

### Community 3 - "Community 3"
Cohesion: 0.04
Nodes (24): AbstractCommand, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand, DevCommand (+16 more)

### Community 4 - "Community 4"
Cohesion: 0.06
Nodes (10): JavelinCli, HomeController, resolve(), DefaultExceptionHandlerTest, Response, ResponseTest, RouterTest, ViewTest (+2 more)

### Community 5 - "Community 5"
Cohesion: 0.06
Nodes (9): RequestInputTest, Html, Input, AdultAgeRule, Validation, AbstractWorkspaceRule, AdultAgeRule, WorkspaceAgeRule (+1 more)

### Community 6 - "Community 6"
Cohesion: 0.05
Nodes (6): CommandContext, HttpKernel, Route, RouteBuilder, Router, Validator

### Community 7 - "Community 7"
Cohesion: 0.08
Nodes (3): Model, Date, Parser

### Community 8 - "Community 8"
Cohesion: 0.04
Nodes (17): CliException, Command, ConsoleKernel, MakeControllerCommand, MakeModelCommand, MakeProviderCommand, MigrateCommand, RouteListCommand (+9 more)

### Community 9 - "Community 9"
Cohesion: 0.08
Nodes (5): CommandRegistry, Request, Routes, View, PebbleViewExtensions

### Community 10 - "Community 10"
Cohesion: 0.08
Nodes (9): Cache, InMemoryCache, DotenvEnv, YamlConfig, Env, defaults(), PebbleViewRenderer, TestEnv (+1 more)

### Community 11 - "Community 11"
Cohesion: 0.11
Nodes (6): Middleware, Next, Database, JdbcDatabase, JdbcQueryBuilder, QueryBuilder

### Community 12 - "Community 12"
Cohesion: 0.1
Nodes (9): UserController, HtmlResponse, JsonResponse, RedirectResponse, Middleware, Response, RateLimitMiddleware, RequestSizeLimitMiddleware (+1 more)

### Community 13 - "Community 13"
Cohesion: 0.09
Nodes (7): CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleHydratedUser, SampleUser, Model

### Community 14 - "Community 14"
Cohesion: 0.13
Nodes (7): AbstractExtension, Filter, Function, RegisteredExtension, HeadlineFilter, JavelinVersionFunction, JavelinViewExtension

### Community 15 - "Community 15"
Cohesion: 0.18
Nodes (2): ValidationRuleLoader, Str

### Community 16 - "Community 16"
Cohesion: 0.11
Nodes (7): AiGenerateModuleCommand, MakeControllerCommand, MakeMigrationCommand, MakeModelCommand, MakeModuleCommand, MakeServiceCommand, GenerateCommandSupport

### Community 17 - "Community 17"
Cohesion: 0.18
Nodes (1): Application

### Community 18 - "Community 18"
Cohesion: 0.2
Nodes (3): AutoCloseable, Database, HttpServerAdapter

### Community 19 - "Community 19"
Cohesion: 0.22
Nodes (1): QueryBuilder

### Community 20 - "Community 20"
Cohesion: 0.31
Nodes (1): Security

### Community 21 - "Community 21"
Cohesion: 0.4
Nodes (1): Cache

### Community 22 - "Community 22"
Cohesion: 0.4
Nodes (2): AbstractCommand, Command

### Community 23 - "Community 23"
Cohesion: 0.4
Nodes (1): Json

### Community 24 - "Community 24"
Cohesion: 0.4
Nodes (1): Logger

### Community 25 - "Community 25"
Cohesion: 0.5
Nodes (1): ServiceProvider

### Community 26 - "Community 26"
Cohesion: 0.67
Nodes (1): ConsoleKernel

### Community 27 - "Community 27"
Cohesion: 0.67
Nodes (1): Env

### Community 28 - "Community 28"
Cohesion: 0.67
Nodes (1): ExceptionHandler

### Community 29 - "Community 29"
Cohesion: 0.67
Nodes (1): RouteHandler

### Community 30 - "Community 30"
Cohesion: 0.67
Nodes (1): TransactionCallback

### Community 31 - "Community 31"
Cohesion: 0.67
Nodes (1): ViewRenderer

## Knowledge Gaps
- **Thin community `Community 15`** (21 nodes): `ValidationRuleLoader.java`, `Str.java`, `ValidationRuleLoader`, `.discoverRules()`, `.instantiate()`, `.toClassName()`, `Str`, `.capitalize()`, `.defaultIfBlank()`, `.isBlank()`, `.isNotBlank()`, `.mask()`, `.repeat()`, `.requireNonNegative()`, `.Str()`, `.stripAccents()`, `.toCamelCase()`, `.toSlug()`, `.toSnakeCase()`, `.trimToNull()`, `.stringSupportCoversCommonTransforms()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 17`** (15 nodes): `Application`, `.Application()`, `.bind()`, `.boot()`, `.config()`, `.container()`, `.ensureNotBooted()`, `.env()`, `.has()`, `.instance()`, `.make()`, `.register()`, `.router()`, `.singleton()`, `Application.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 19`** (9 nodes): `QueryBuilder`, `.delete()`, `.first()`, `.get()`, `.insert()`, `.paginate()`, `.update()`, `.where()`, `QueryBuilder.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 20`** (9 nodes): `Security.java`, `Security`, `.constantTimeEquals()`, `.randomToken()`, `.reservedWindowsNames()`, `.Security()`, `.sha256Hex()`, `.toHex()`, `.securitySupportHashesTokensAndSanitizesNames()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 21`** (5 nodes): `Cache`, `.forget()`, `.get()`, `.put()`, `Cache.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 22`** (5 nodes): `AbstractCommand`, `.AbstractCommand()`, `.name()`, `Command`, `AbstractCommand.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 23`** (5 nodes): `Json`, `.error()`, `.Json()`, `.ok()`, `Json.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 24`** (5 nodes): `Logger`, `.error()`, `.info()`, `.warn()`, `Logger.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 25`** (4 nodes): `ServiceProvider`, `.boot()`, `.register()`, `ServiceProvider.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 26`** (3 nodes): `ConsoleKernel`, `.run()`, `ConsoleKernel.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 27`** (3 nodes): `Env`, `.get()`, `Env.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 28`** (3 nodes): `ExceptionHandler`, `.handle()`, `ExceptionHandler.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 29`** (3 nodes): `RouteHandler`, `.handle()`, `RouteHandler.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 30`** (3 nodes): `TransactionCallback`, `.run()`, `TransactionCallback.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 31`** (3 nodes): `ViewRenderer`, `.render()`, `ViewRenderer.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Collection` connect `Community 1` to `Community 0`, `Community 14`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Why does `Slf4jLogger` connect `Community 8` to `Community 0`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.05 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.06 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.04 - nodes in this community are weakly interconnected._
- **Should `Community 3` be split into smaller, more focused modules?**
  _Cohesion score 0.04 - nodes in this community are weakly interconnected._
- **Should `Community 4` be split into smaller, more focused modules?**
  _Cohesion score 0.06 - nodes in this community are weakly interconnected._