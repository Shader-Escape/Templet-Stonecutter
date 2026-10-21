# Exit Stonecutter 模板

针对版本代码变化不大的模组,用stonecutter开发iris兼容附属更轻松。

这是一个 Fabric 模组模板，它默认引入[Exit 库(emergency-exit)](https://github.com/Shader-Escape/emergency-exit)。

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

## 许可证

[CC0 1.0](LICENSE)。
