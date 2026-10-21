# Exit Stonecutter 模板

针对版本代码变化不大的模组,用stonecutter开发iris兼容附属更轻松。

这是一个 Fabric 模组模板,用来给已有模组做 Iris(光影包)兼容附属。它默认引入
[Exit 库(emergency-exit)](https://github.com/hotpad100c/emergency-exit):把你的渲染包进 `Exit.portal`、`Exit.draw`
或 `Exit.batch`,开着光影包时这些渲染会绕过光影包,画完后再按深度合并回画面。

模板以 Minecraft 26.3 为原型,用 [Stonecutter](https://stonecutter.kikugie.dev/) 让同一份代码构建多个 Minecraft
版本,并跨越 1.21.11 这条线:

| Minecraft | 混淆 | Loom 插件 | 依赖写法 | Java |
|---|---|---|---|---|
| ≤ 1.21.11 | 是 | `net.fabricmc.fabric-loom-remap`,`mappings(loom.officialMojangMappings())`,产物经过 `remapJar` | `modImplementation` | 21 |
| 26.x | 否 | `net.fabricmc.fabric-loom`,没有 mappings,不重映射 | `implementation` | 25 |

`build.gradle.kts` 按 Stonecutter 当前版本自动切换这两套写法,你不需要为每个版本写不同的构建脚本。

自带的版本:`1.21.1`、`1.21.11`、`26.3`(主版本,源码里写的就是 26.3 的代码)。

## 目录

```
settings.gradle.kts           版本列表(stonecutter { versions(...) })
stonecutter.gradle.kts        当前激活版本(stonecutter active)
build.gradle.kts              所有版本共用的构建脚本
gradle.properties             模组 id、名称、版本,Fabric Loader 版本
versions/<版本>/gradle.properties  每个版本的 Minecraft、Fabric API、Exit 版本
src/main/java/.../ExampleMod.java              入口类
src/main/java/.../mixin/LevelRendererMixin.java  接 Exit 的地方
```

## 在哪里接 Exit

`LevelRendererMixin` 在关卡渲染结束前(Exit 合并画面之前)调用 `Exit.portal(ExampleMod::render)`。把你自己的渲染代码写进
`ExampleMod.render()` 就行。

做别的模组的附属时,更常见的写法是 mixin 进那个模组的渲染方法,把它的渲染调用包起来:

```java
@WrapOperation(method = "...", at = @At(value = "INVOKE", target = "..."))
private void wrap(..., Operation<Void> original) {
    Exit.portal(() -> original.call(...));
}
```

Exit 的完整用法(`draw`、`batch`、`portal`、`redirect`,以及每个版本该用哪个 Fabric 渲染事件)见
[emergency-exit 的 README](https://github.com/hotpad100c/emergency-exit) 和它的 `ChangeLog.md`。

### 版本差异怎么写

小差异直接用 Stonecutter 的注释条件,例如 26.2 起 `LevelRenderer.renderLevel` 改名为 `render`:

```java
//? if >=26.2 {
@Inject(method = "render", at = @At("RETURN"))
//?} else {
/*@Inject(method = "renderLevel", at = @At("RETURN"))
*///?}
```

整批的改名(包名、类名)可以在 `stonecutter.gradle.kts` 里用 `stonecutter parameters { replacements { ... } }`,写法见
[Stonecutter 文档](https://stonecutter.kikugie.dev/wiki/config/params)。

## Exit 库的版本

Exit 从 [JitPack](https://jitpack.io/#hotpad100c/emergency-exit) 引入,坐标是:

```
com.github.hotpad100c.emergency-exit:emergency_exit-fabric:<版本>
```

emergency-exit 每个 Minecraft 版本一个分支:26.3 在 `master`,其它版本在 `port/<版本>`。JitPack 用分支名做版本,
`/` 换成 `~`,再加 `-SNAPSHOT`:

| Minecraft | `deps.exit` |
|---|---|
| 26.3 | `master-SNAPSHOT` |
| 1.21.11 | `port~1.21.11-SNAPSHOT` |
| 1.21.1 | `port~1.21.1-SNAPSHOT` |

也可以写 tag 或某次提交的短哈希,得到固定的版本。

构建时先找本机的 `mavenLocal()`,再找 JitPack。所以 JitPack 还没构建好某个分支时,可以在 emergency-exit 那个分支里运行
`./gradlew publishToMavenLocal`,本模板就能用上。

## 添加版本

1. 在 `settings.gradle.kts` 的 `versions(...)` 里加上版本号,例如 `"1.21.10"`。
2. 新建 `versions/1.21.10/gradle.properties`:

   ```properties
   minecraft=1.21.10
   deps.fabric_api=0.138.4+1.21.10
   deps.exit=port~1.21.10-SNAPSHOT
   mod.mc_dep=1.21.10
   ```

   Fabric API 版本查 <https://fabricmc.net/develop>。
3. 构建这个版本,按编译错误用 Stonecutter 条件处理代码差异。

不用改 `build.gradle.kts`:≤ 1.21.11 的版本自动按混淆处理,26.x 自动按不混淆处理。

## 构建

需要 JDK 25 来运行 Gradle(1.21.x 的代码会以 Java 21 编译)。

```bash
./gradlew build
```

构建所有版本。只构建一个版本:

```bash
./gradlew :1.21.11:build
```

把所有版本的 jar 收集到 `build/libs/<模组版本>/`:

```bash
./gradlew buildAndCollect
```

各版本自己的产物在 `versions/<版本>/build/libs/`(混淆版本的 jar 已经过 `remapJar`,可以直接装进游戏)。

在 IDE 里切换正在编辑的版本(源码会按该版本改写注释条件):运行 Gradle 任务 `"Set active project to 1.21.11"`。
提交前运行 `"Reset active project"` 切回 `26.3`。

## 开始一个新模组

1. 改 `gradle.properties` 里的 `mod.id`、`mod.name`、`mod.group`。
2. 把 `src/main/java/com/example/exittemplate` 改成你的包名,同步修改 `fabric.mod.json` 的入口类和
   `exit_template.mixins.json`(文件名和 `package`)。
3. 在 `fabric.mod.json` 里填作者、描述,并在 `depends` 里加上你要兼容的模组。

## 许可证

[CC0 1.0](LICENSE)。
