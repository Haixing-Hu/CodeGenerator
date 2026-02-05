import org.gradle.api.tasks.testing.Test

plugins {
  id("java")
  id("org.jetbrains.intellij.platform") version "2.11.0"
}

group = "me.lotabout"
version = "2.0.6"

repositories {
  mavenCentral()
  intellijPlatform {
    defaultRepositories()
  }
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(18))
  }
}

dependencies {
  intellijPlatform {
    create("IC", "2025.1")
    bundledPlugin("com.intellij.java")
    pluginVerifier()
    zipSigner()
    instrumentationTools()
  }
  implementation("org.apache.commons:commons-lang3:3.12.0")
  implementation("org.apache.velocity.tools:velocity-tools-generic:3.1")  {
    exclude(group = "org.apache.velocity", module = "velocity-engine-core")
    exclude(group = "org.slf4j", module = "slf4j-api")
  }
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.mockito:mockito-core:5.10.0")
  testImplementation("org.mockito:mockito-junit-jupiter:5.10.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "18"
    targetCompatibility = "18"
    options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
  }

  // 禁用buildSearchableOptions任务
  named("buildSearchableOptions") {
    enabled = false
  }

  // 允许执行测试任务（用于问题复现）
  named<Test>("test") {
    enabled = true
    useJUnitPlatform()
  }

  patchPluginXml {
    sinceBuild.set("251")
    untilBuild.set("253.*")
    changeNotes.set("""
      <ul>
        <li>修复：方法条目名称现在在模板中正确解析为方法名</li>
        <li>支持 IntelliJ IDEA 2025.1 - 2025.3</li>
        <li>修复设置对话框兼容性问题</li>
        <li>修复打开插件设置时的空指针异常</li>
      </ul>
    """)
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}