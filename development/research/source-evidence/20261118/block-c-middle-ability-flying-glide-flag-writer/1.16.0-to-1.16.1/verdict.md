# Independent audit: 1.16.0 → 1.16.1 ability-flight / Elytra cleanup

## Verdict

**No 1.16.0→1.16.1 source difference; correction required to the report summary.** In both releases, for a local player already lawfully flying (`mayfly` and `flying`), with glide flag 7 active, airborne and not a passenger, dry-air travel with a valid chest Elytra enters the glide branch and performs its `move`. The source report's Result section reverses the final two writes: it says flag 7 is cleared before fall distance is reset. Actual `Player.travel` order is fall distance reset first, then shared flag 7 cleared. The report's detailed trace bullet gives the correct order. This verdict covers only that path and its post-move cleanup.

## Immutable inputs and source identity

Audited source claim: `c-mid-116-1161-ability-flight-glide-clear-source/report.md`, SHA-256 `ccea627197d20a196868ad82b53d8bf977ef7ac9c64cf97d528ff08750328d32`.

- `decompiled_minecraft/1.16/mojmap` identifies version metadata ID `1.16` (1.16.0). Ready marker SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; provenance SHA-256 `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`; source-manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`.
- `decompiled_minecraft/1.16.1/mojmap` identifies exact version ID `1.16.1`. Ready marker SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; provenance SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`.
- Both source manifests and direct file hashes agree for all four cited classes. Each is byte-identical across releases: `LocalPlayer.java` `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; `LivingEntity.java` `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; `ElytraItem.java` `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d`.

## Independent trace

- `LivingEntity.aiStep` calls `updateFallFlying()` before virtual `travel(...)` (`LivingEntity.java:2452-2458`). An already-set flag 7 is retained for an airborne, non-passenger player without Levitation when the chest slot contains a fly-enabled Elytra (`:2476-2494`). `isFallFlying()` reads flag 7 (`:2875-2877`).
- Dry air selects the glide branch after water and lava checks (`LivingEntity.java:1921-1977`). That branch may write `fallDistance = 1.0F` before movement, applies glide velocity, and calls `move(MoverType.SELF, ...)` (`:1977-2006`). Its later flag-7 clear requires `onGround && !level.isClientSide` (`:2017-2019`), so it does not clear the local client player's flag in this scenario.
- `Player.travel` enters the ability-flight branch when `abilities.flying && !isPassenger()` (`Player.java:1395-1404`). It calls `super.travel(vec3)` first; that reaches the glide `move`. Then the complete relevant order is: read resulting velocity into `vec33`; write velocity `(vec33.x, i * 0.6, vec33.z)`; restore saved `flyingSpeed`; write `fallDistance = 0.0F`; call `setSharedFlag(7, false)`; and, after the branch, call `checkMovementStatistics(...)`. The two cleanup writes are unconditional within this branch and occur after the nested move.
- `LocalPlayer.java:750-755` confirms the new Elytra-launch input path requires `!abilities.flying`; it is not needed for this already-active flight case. `ElytraItem.java:17-18` provides the same fly-enabled durability predicate in both releases.

The trace and source identities establish no boundary for this operation between these endpoints. They do not establish full-pair coverage, other tick slices, or runtime parity. No implementation, build, test, network, or runtime action was performed.
