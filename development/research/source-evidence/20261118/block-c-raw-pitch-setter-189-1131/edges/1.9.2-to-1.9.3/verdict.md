# Independent source audit verdict: no pitch delta

## Scope

Blinded Lead C audit of the ordinary local current/previous pitch update and clamp, plus the first current-pitch use in the dry active-local Elytra flight branch, comparing Minecraft 1.9.2 with 1.9.3 under Ornithe Feather. This is a bounded operation verdict; it does not close full-pair coverage or establish an intermediate patch boundary.

## Frozen source identity

The canonical roots are `decompiled_minecraft/<version>/ornithe-feather/` in the primary checkout. Before reading source members, I froze a whole-tree digest as SHA-256 over the UTF-8 bytes of sorted rows `relative/path<TAB>file-sha256<TAB>byte-length`, LF-terminated, including `.decompile-provenance.json`:

- 1.9.2 tree: `828b9f9c102203c5319dc9d78ad3fa3135a35b72ac61f85517e53cb40874efb0` (1,820 files; 9,513,722 bytes); provenance manifest bytes: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`.
- 1.9.3 tree: `fa28ef66d991176478ad9a50dbdf728884216cc6ca44ab395ea2d0f5e146f4cf` (1,820 files; 9,547,715 bytes); provenance manifest bytes: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.

Both manifests use the legacy key set `pipeline`, `runtime`, `tools`, `inputs`, `sources` (no explicit schema-version field). I accepted this older schema only after byte-binding each manifest and verifying that all 1,819 listed source hashes on each side match the corresponding files, with no missing, mismatched, or unlisted source files. The manifests identify Feather Gen2 build.2 for the exact release on each side and bind the matching raw client, exact-version metadata, Feather metadata, Feather archive, tool hashes, and all generated source hashes. Selected exact input pins:

- 1.9.2 client `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`; Feather metadata `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1`; Feather Gen2 `1.9.2+build.2` archive `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; exact-version metadata `a0b96ff1dabf71650c40957b615188067e6ee338e3a30522b4c3fd8613a25c0f`.
- 1.9.3 client `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`; Feather metadata `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`; Feather Gen2 `1.9.3+build.2` archive `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; exact-version metadata `2ec3b7abc12f0ad4d4e3c777dc0c429fa948ffd4d1bdbb89a81aecbd30021be5`.

## Comparison

**Ordinary local pitch setter:** In both releases, `GameRenderer` calls `Entity.updateLocalPlayerCamera(h, i * j)` in both smooth and non-smooth mouse-look paths (1.9.2 lines 842–854; 1.9.3 lines 843–855). `Entity.updateLocalPlayerCamera` is identical in operation and ordering (1.9.2 `Entity.java:283–294`; 1.9.3 `Entity.java:286–297`): save old pitch in float local `f`; update yaw; assign `pitch = (float)(pitch - inputPitch * 0.15)`; clamp the float pitch to `[-90.0F, 90.0F]`; then update `lastPitch = lastPitch + (pitch - f)`. The multiplication uses the unsuffixed double literal `0.15`, with the cast to float on the completed subtraction before clamping. A finite computed value outside the bounds is saturated by the same float `MathHelper.clamp` implementation in both versions (`MathHelper.java:80–85`); the previous pitch receives the clamped-current-minus-old-current delta. No cast, arithmetic, clamp, state-write, or ordering difference was found.

**First current-pitch use in dry active-local Elytra flight:** `LivingEntity.moveRelative` has the same gate on both sides: locally controlled or logical-side movement; water/lava checks permit bypass for a flying player; then `isFallFlying()` enters the flight branch (1.9.2 `LivingEntity.java:1293–1303`; 1.9.3 `LivingEntity.java:1302–1312`). Its first current-pitch use is `getLookVector()`, which requests `getRotationVector(1.0F)` and takes the current `this.pitch` branch (1.9.2 `LivingEntity.java:1808–1819`; 1.9.3 `LivingEntity.java:1817–1828`). The immediately following scalar angle is `float i = this.pitch * (float)(Math.PI / 180.0)` (1.9.2 line 1303; 1.9.3 line 1312). Both the vector path, the explicit conversion, and the enclosing gates are unchanged. No dry active-local movement difference was found here.

The relevant pinned source hashes are:

| File | 1.9.2 SHA-256 | 1.9.3 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| `net/minecraft/client/render/GameRenderer.java` | `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709` | `e08c2034c7fd4621a76ff5a4ba26dd80e065c895bc5c0a4120d4d36141203d6b` |

## Verdict

**No difference accepted for this bounded pitch operation in 1.9.2 → 1.9.3.** The evidence confirms an unchanged direct local pitch update and unchanged first current-pitch reads in dry active-local Elytra flight. This verdict is limited to those operations and exact endpoint sources.
## Phase-two comparison with the frozen source report

The authorized source report was rehashed before inspection and matched its assigned frozen SHA-256 `f14bbd785ee4f9bd5dba1af4b93e2941c0a78b7d2b748b10dff181565772108d`. Its exact input pins, two manifest byte hashes, and relevant decompiled-file hashes agree with the identities frozen in this verdict. Its bounded no-difference result agrees with the independent comparison above. The report also records equal extracted-body hashes on both sides for `Entity.updateLocalPlayerCamera` (`3970cf44a7c1bb64be023db7a3b83f1d0c3f8d8d0d152df48c330e058d8293ac`), `LivingEntity.moveRelative` (`8935df8b81194575e97189f46e7e59422988118ba73b1eca880c8340699359dd`), and the `LivingEntity.getLookVector` override (`73297ebeaab31abac9f803dfb0b1899d2c0762013655975eaea087ebb3ccfff2c`). These extracted-body digests are attributed to the frozen author report; this phase did not revisit source inputs. No disagreement or changed verdict.