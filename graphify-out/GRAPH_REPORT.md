# Graph Report - javelin-framework  (2026-05-01)

## Corpus Check
- 154 files · ~35,472 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 1278 nodes · 3849 edges · 33 communities detected
- Extraction: 46% EXTRACTED · 54% INFERRED · 0% AMBIGUOUS · INFERRED: 2083 edges (avg confidence: 0.8)
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
- [[_COMMUNITY_Community 32|Community 32]]

## God Nodes (most connected - your core abstractions)
1. `Collection` - 54 edges
2. `Model` - 37 edges
3. `Handle` - 27 edges
4. `Validation` - 27 edges
5. `Request` - 25 edges
6. `SupportUtilitiesTest` - 24 edges
7. `Handle` - 22 edges
8. `parse()` - 18 edges
9. `Router` - 18 edges
10. `MigrationRunner` - 18 edges

## Surprising Connections (you probably didn't know these)
- `JavelinViewExtension` --extends--> `AbstractExtension`  [EXTRACTED]
  demo-app\app\views\JavelinViewExtension.java →   _Bridges community 1 → community 15_
- `YamlConfig` --implements--> `Config`  [EXTRACTED]
  modules\javelin-config\src\main\java\io\javelin\config\YamlConfig.java →   _Bridges community 8 → community 2_
- `TestConfig` --implements--> `Config`  [EXTRACTED]
  modules\javelin-starter\src\test\java\io\javelin\starter\JavelinStarterServiceProviderTest.java →   _Bridges community 2 → community 1_
- `ServeCommand` --extends--> `AbstractCommand`  [EXTRACTED]
  modules\javelin-console\src\main\java\io\javelin\cli\commands\ServeCommand.java →   _Bridges community 6 → community 2_
- `MakeMigrationCommand` --extends--> `GenerateCommandSupport`  [EXTRACTED]
  modules\javelin-console\src\main\java\io\javelin\cli\commands\MakeMigrationCommand.java →   _Bridges community 16 → community 9_

## Communities

### Community 0 - "Community 0"
Cohesion: 0.04
Nodes (16): CommandContext, CommandGenerationTest, ProjectLauncher, MigrationCommandTest, GeneratorSupport, Main, FileGenerator, Names (+8 more)

### Community 1 - "Community 1"
Cohesion: 0.04
Nodes (19): HomeController, CastedArticle, EditableUser, ModelTest, RecordingDatabase, RecordingQueryBuilder, SampleHydratedUser, SampleUser (+11 more)

### Community 2 - "Community 2"
Cohesion: 0.04
Nodes (28): CacheServiceProvider, ServeCommand, Config, ConfigServiceProvider, DotenvEnv, YamlConfigTest, ConsoleServiceProvider, Config (+20 more)

### Community 3 - "Community 3"
Cohesion: 0.05
Nodes (23): DefaultExceptionHandler, advanceBoundary(), empty(), freezeUploads(), indexOf(), matches(), parse(), parseDisposition() (+15 more)

### Community 4 - "Community 4"
Cohesion: 0.04
Nodes (17): RecordingDatabase, RecordingQueryBuilder, JavelinConsole, ConsoleKernel, Middleware, Next, Database, JdbcDatabase (+9 more)

### Community 5 - "Community 5"
Cohesion: 0.05
Nodes (6): Arr, Collection, File, Html, Obj, SupportUtilitiesTest

### Community 6 - "Community 6"
Cohesion: 0.04
Nodes (23): AbstractCommand, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand, DevCommand (+15 more)

### Community 7 - "Community 7"
Cohesion: 0.05
Nodes (8): WorkspaceStarterRule, AdultAgeRule, Validation, Validator, AbstractWorkspaceRule, AdultAgeRule, WorkspaceAgeRule, ValidationRule

### Community 8 - "Community 8"
Cohesion: 0.07
Nodes (10): CliException, CommandRegistry, JavelinCli, YamlConfig, resolve(), DefaultExceptionHandlerTest, RouterTest, ViewTest (+2 more)

### Community 9 - "Community 9"
Cohesion: 0.07
Nodes (7): MakeMigrationCommand, StaticAssetResolver, Ai, Handle, system(), user(), Str

### Community 10 - "Community 10"
Cohesion: 0.05
Nodes (15): UserController, HtmlResponse, JsonResponse, Log, NoopLogger, RedirectResponse, close(), StaticAssetResolverTest (+7 more)

### Community 11 - "Community 11"
Cohesion: 0.1
Nodes (6): JdkHttpServerIntegrationTest, bodyText(), contentType(), Handle, Http, successful()

### Community 12 - "Community 12"
Cohesion: 0.07
Nodes (5): HttpKernel, Route, RouteBuilder, Router, Routes

### Community 13 - "Community 13"
Cohesion: 0.12
Nodes (2): Model, longValue()

### Community 14 - "Community 14"
Cohesion: 0.09
Nodes (9): Command, ConsoleKernel, MakeControllerCommand, MakeModelCommand, MakeProviderCommand, MigrateCommand, RouteListCommand, ServeCommand (+1 more)

### Community 15 - "Community 15"
Cohesion: 0.17
Nodes (6): AbstractExtension, Filter, Function, RegisteredExtension, HeadlineFilter, JavelinVersionFunction

### Community 16 - "Community 16"
Cohesion: 0.12
Nodes (6): AiGenerateModuleCommand, MakeControllerCommand, MakeModelCommand, MakeModuleCommand, MakeServiceCommand, GenerateCommandSupport

### Community 17 - "Community 17"
Cohesion: 0.22
Nodes (2): Date, Parser

### Community 18 - "Community 18"
Cohesion: 0.18
Nodes (1): Application

### Community 19 - "Community 19"
Cohesion: 0.17
Nodes (4): Cache, InMemoryCache, PebbleViewRenderer, ViewRenderer

### Community 20 - "Community 20"
Cohesion: 0.18
Nodes (3): AutoCloseable, Database, HttpServerAdapter

### Community 21 - "Community 21"
Cohesion: 0.22
Nodes (1): QueryBuilder

### Community 22 - "Community 22"
Cohesion: 0.4
Nodes (1): Cache

### Community 23 - "Community 23"
Cohesion: 0.4
Nodes (2): AbstractCommand, Command

### Community 24 - "Community 24"
Cohesion: 0.4
Nodes (1): Json

### Community 25 - "Community 25"
Cohesion: 0.4
Nodes (1): Logger

### Community 26 - "Community 26"
Cohesion: 0.5
Nodes (1): ServiceProvider

### Community 27 - "Community 27"
Cohesion: 0.67
Nodes (1): ConsoleKernel

### Community 28 - "Community 28"
Cohesion: 0.67
Nodes (1): Env

### Community 29 - "Community 29"
Cohesion: 0.67
Nodes (1): ExceptionHandler

### Community 30 - "Community 30"
Cohesion: 0.67
Nodes (1): RouteHandler

### Community 31 - "Community 31"
Cohesion: 0.67
Nodes (1): TransactionCallback

### Community 32 - "Community 32"
Cohesion: 0.67
Nodes (1): ViewRenderer

## Knowledge Gaps
- **Thin community `Community 13`** (38 nodes): `Model`, `.all()`, `.assignAttributes()`, `.assignId()`, `.attribute()`, `.attributes()`, `.casts()`, `.castValue()`, `.database()`, `.defaultTableName()`, `.delete()`, `.fill()`, `.fillable()`, `.find()`, `.findOrFail()`, `.firstWhere()`, `.forceFill()`, `.guarded()`, `.hydrate()`, `.id()`, `.idOf()`, `.isVowel()`, `.primaryKey()`, `.query()`, `.save()`, `.table()`, `.tableName()`, `.toBoolean()`, `.toEnum()`, `.toInteger()`, `.toLocalDate()`, `.toLocalDateTime()`, `.toLong()`, `.toMap()`, `.where()`, `.modelHonorsFillableAndGuardedAttributes()`, `longValue()`, `Model.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 17`** (16 nodes): `Date.java`, `Date`, `.addDays()`, `.Date()`, `.format()`, `.isFuture()`, `.isPast()`, `.now()`, `.parseDate()`, `.parseDateTime()`, `.requireDate()`, `.requireDateTime()`, `.subtractDays()`, `.today()`, `Parser`, `.dateSupportParsesFormatsAndComparesDates()`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 18`** (15 nodes): `Application`, `.Application()`, `.bind()`, `.boot()`, `.config()`, `.container()`, `.ensureNotBooted()`, `.env()`, `.has()`, `.instance()`, `.make()`, `.register()`, `.router()`, `.singleton()`, `Application.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 21`** (9 nodes): `QueryBuilder`, `.delete()`, `.first()`, `.get()`, `.insert()`, `.paginate()`, `.update()`, `.where()`, `QueryBuilder.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 22`** (5 nodes): `Cache`, `.forget()`, `.get()`, `.put()`, `Cache.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 23`** (5 nodes): `AbstractCommand`, `.AbstractCommand()`, `.name()`, `Command`, `AbstractCommand.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 24`** (5 nodes): `Json`, `.error()`, `.Json()`, `.ok()`, `Json.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 25`** (5 nodes): `Logger`, `.error()`, `.info()`, `.warn()`, `Logger.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 26`** (4 nodes): `ServiceProvider`, `.boot()`, `.register()`, `ServiceProvider.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 27`** (3 nodes): `ConsoleKernel`, `.run()`, `ConsoleKernel.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 28`** (3 nodes): `Env`, `.get()`, `Env.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 29`** (3 nodes): `ExceptionHandler`, `.handle()`, `ExceptionHandler.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 30`** (3 nodes): `RouteHandler`, `.handle()`, `RouteHandler.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 31`** (3 nodes): `TransactionCallback`, `.run()`, `TransactionCallback.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.
- **Thin community `Community 32`** (3 nodes): `ViewRenderer`, `.render()`, `ViewRenderer.java`
  Too small to be a meaningful cluster - may be noise or needs more connections extracted.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Slf4jLogger` connect `Community 10` to `Community 0`?**
  _High betweenness centrality (0.044) - this node is a cross-community bridge._
- **Why does `Model` connect `Community 13` to `Community 0`, `Community 5`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.04 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.04 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.04 - nodes in this community are weakly interconnected._
- **Should `Community 3` be split into smaller, more focused modules?**
  _Cohesion score 0.05 - nodes in this community are weakly interconnected._
- **Should `Community 4` be split into smaller, more focused modules?**
  _Cohesion score 0.04 - nodes in this community are weakly interconnected._