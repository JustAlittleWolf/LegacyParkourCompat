# Independent audit verdict: ability-flight Elytra glide clear

**Verdict: ACCEPT — no difference for the assigned operation.** The source report's bounded claim is supported by the exact source pair. For a local, dry, airborne, non-riding player with a usable chest Elytra, setting lawful creative flight while flag 7 is set enters the same path in both releases. The Elytra branch performs the actual `move(...)` inside `super.moveRelative(...)`; after it returns, `PlayerEntity.moveRelative` restores vertical velocity and `speedInAir`, writes `fallDistance = 0.0F`, then directly clears flag 7. Thus the actual move precedes the direct flag clear, and the fall-distance reset is a distinct immediately preceding write. On the logical client, `LivingEntity.flyingTick()` does not rewrite flag 7 because its setter is server-gated; this lets the player override take the glide path before clearing it.

## Immutable inputs checked

- Audited source report: `c-mid-1132-114-ability-flight-glide-clear-source/report.md`, SHA-256 `DF02E50A2A96C2608E65D96EC7FECA3D4B0D7C5F7AE83F3FFC75BA59B484A22E`.
- Exact releases and mapping: 1.13.2 / Ornithe Feather `feather-gen2-1.13.2+build.2`; 1.14.0 (release ID `1.14`) / Ornithe Feather `net.ornithemc:feather-gen2:1.14+build.2`.
- 1.13.2 source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; preparation-provenance SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`.
- 1.14 source-manifest SHA-256 `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`; preparation-provenance SHA-256 `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa`.
- Relevant source SHA-256 values match the manifests: 1.13.2 `LocalClientPlayerEntity.java` `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`, `LivingEntity.java` `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, `PlayerEntity.java` `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; 1.14 equivalents `2b1ad3b3416949a9da2607a3ec2251aa69b2d9eebd76638c92e20749d625338f`, `1db4dd1aa95a06c48511e5567c0d3836f2a6e5a12a5cf03ab5c892e429198b79`, `f73879bd42103fa45f39cfe178ac82c7574c6306f00555af7f7e795976275dd7`.

## Audit trace

- Local creative-flight toggle: 1.13.2 `LocalClientPlayerEntity.java:760–774`; 1.14 `LocalClientPlayerEntity.java:692–706`. Both require `abilities.canFly`, the second-jump path, and `!isSwimming()`, without a grounded or `isFallFlying()` gate.
- Tick and client Elytra validation: 1.13.2 `LivingEntity.java:1920–1927, 1938–1956`; 1.14 `LivingEntity.java:2214–2221, 2232–2250`. Both call `flyingTick()` before `moveRelative(...)`; the flag setter inside `flyingTick()` is guarded by `!world.isClient`.
- Glide movement and direct clear: 1.13.2 base `LivingEntity.moveRelative` runs the Elytra `move(MoverType.SELF, ...)` at `1523` and server-only flag clear at `1534–1535`; `PlayerEntity.moveRelative` then resets fall distance and clears flag 7 at `1458–1462`. In 1.14, the same order is base `LivingEntity.moveRelative` `1818–1831`, then `PlayerEntity.moveRelative` `1380–1385`. The accepted claim is restricted to this path; no full-pair coverage or runtime parity is implied.

Signed: independent source auditor, `/root/c_mid_1132_114_ability_flight_glide_clear_audit`.