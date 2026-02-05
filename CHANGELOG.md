# Changelog

All notable changes to this project will be documented in this file.

## [2.0.7] - 2026-02-05

### Fixed
- Fixed Velocity Engine class loader conflict by excluding velocity-engine-core dependency that conflicts with IntelliJ Platform
- Fixed method entry name now correctly resolves to method name in templates (MethodEntry.getName() implementation)

### Changed
- Removed ToolManager usage to avoid class loading conflicts, directly creating VelocityContext instead
- Upgraded Gradle wrapper to 8.13
- Upgraded IntelliJ Platform Gradle Plugin to 2.11.0
- Java toolchain set to version 18

### Added
- Unit tests for MethodEntry to verify getName() behavior

## [2.0.6] - 2026-02-05

### Fixed
- Method entry name resolution in Velocity templates

## [2.0.5] - 2025-02-20

### Added
- Support for IntelliJ IDEA 2025.1 - 2025.3

### Fixed
- Settings dialog compatibility issue
- NullPointerException when opening plugin settings
