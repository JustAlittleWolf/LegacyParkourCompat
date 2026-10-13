# MCPK finding snapshot: 1.15 Honey block movement

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze the full release pair.

## MCPK claims

The MCPK [Version Differences page](https://www.mcpk.wiki/wiki/Version_Differences), last edited 2026-04-17 12:51, lists Honey blocks as added in 1.15, with a `0.875 × 0.9375 × 0.875` collision box, SoulSand-like slowdown and reduced initial jump velocity, effects for blocks up to `0.5` above, and a slow-fall-like wall slide. Only these player movement and collision claims are in scope; fall-damage behavior is excluded.

## Source adjudication

The exact 1.15.2 Mojmap endpoint confirms the block and movement operations. The 1.14.4 Mojmap `Blocks.java` contains no Honey block registration; this pair therefore bounds presence to after 1.14.4 and by 1.15.2, without asserting an uninspected earliest patch inside 1.15.

- **Shape:** `HoneyBlock.SHAPE` is `Block.box(1, 0, 1, 15, 15, 15)` (`HoneyBlock.java:21–35`), giving a centered width of `14/16 = 0.875` and height `15/16 = 0.9375`, as the wiki states.
- **Horizontal and jump factors:** `Blocks.HONEY_BLOCK` is registered with `speedFactor(0.4F)` and `jumpFactor(0.5F)` (`Blocks.java:2118–2123`). After `checkInsideBlocks`, `Entity` multiplies X and Z velocity by the selected block speed factor and preserves Y (`Entity.java:533–543`). Its helper selects the current block or one block below the entity (`Entity.java:584–601`); the below probe uses entity X/Z and `boundingBox.minY - 0.5000001` (`:600–601`). Thus a Honey block selected at or below the player scales horizontal velocity by `0.4` at this point in the entity tick.
- `LivingEntity.getJumpPower` returns `0.42F * getBlockJumpFactor()` (`LivingEntity.java:1799–1801`). `jumpFromGround` assigns this value as vertical velocity before adding the optional Jump Boost increment (`:1803–1810`). With Honey selected and no Jump Boost, the initial Y velocity is `0.21F`. This verifies the wiki’s initial-impulse explanation; the wiki’s `0.383m` total jump-height figure is not established by the initial impulse alone and is not asserted in this snapshot.
- **Wall slide guards and response:** `entityInside` invokes the slide path only when `isSlidingDown` is true (`HoneyBlock.java:49–58`). It requires the entity not on ground (`:60–63`), Y at or below `blockY + 0.9375 - 1.0E-7` (`:65–67`), vertical velocity strictly below `-0.08` (`:69–71`), and either horizontal center distance plus `1.0E-7` to exceed `0.4375 + width/2` (`:73–76`). When sliding, if Y velocity is below `-0.13`, X/Z are scaled by `-0.05 / velocityY`; otherwise they are retained. Y is set to `-0.05` and fall distance is reset (`:85–95`). This supports the wiki’s wall-slide description with its exact endpoint conditions.

The source describes generic entity callbacks and block factors, but only the player result is adjudicated here. The candidate interaction for jump height above Honey follows the same `minY - 0.5000001` sampling rule; exact floating-point boundary cases and total jump displacement remain open.

## Source identities

Canonical ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both markers identify the exact release and Mojmap namespace. The source manifest hashes below match their ready markers, and each cited Java file hash matches its row in that manifest.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.14.4/mojmap` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` |
| `1.15.2/mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` |

Cited Java file SHA-256 values, relative to each endpoint’s `mojmap/` directory:

- `1.14.4`: `net/minecraft/world/level/block/Blocks.java` — `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9`.
- `1.15.2`: `net/minecraft/world/entity/Entity.java` — `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `LivingEntity.java` — `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `level/block/Blocks.java` — `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9`; `level/block/HoneyBlock.java` — `40760aeb3c084f1143e87e1e057f18165492eb01b8fce0815dfdd0882cdc8a03`.

This finding uses Mojmap source endpoints and does not depend on any revised Feather derived JAR. It is independent of other audit lanes; broad catalog coverage and independent wiki-lane acceptance remain open.
