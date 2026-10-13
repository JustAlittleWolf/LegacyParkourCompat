# MCPK finding snapshot: 1.17 Big Dripleaf collision timing

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze the full release pair.

## MCPK claim

The MCPK [Version Differences page](https://www.mcpk.wiki/wiki/Version_Differences), last edited 2026-04-17 12:51, describes Big Dripleaf tilt as changing its collision shape. It says standing on the leaf for 20 ticks lowers the hitbox slightly and that it becomes non-colliding after a few more ticks. This snapshot checks the player-triggered collision-state sequence and its guards. The page also mentions server-side behavior, redstone, and projectile activation; only the player collision path is adjudicated here.

## Source adjudication

The 1.16.5 Mojmap endpoint has no `BIG_DRIPLEAF` registration in `Blocks.java`; 1.17.1 registers it and provides the tilt/collision behavior below. This bounds presence to after 1.16.5 and by 1.17.1; it does not identify the exact first 1.17 patch.

- **Player trigger:** `entityInside` changes the state only server-side, and only when tilt is `NONE`, the entity is on the ground, its position Y is strictly greater than `blockY + 0.6875F`, and the block has no neighbor signal (`BigDripleafBlock.java:182–186, 218–220`). The wiki’s generic “standing” description omits these exact guards.
- **Scheduled shape sequence:** contact sets tilt to `UNSTABLE` immediately and schedules the next state after 10 ticks (`:45–49, 222–230`). `NONE` and `UNSTABLE` use the same leaf collision box, from Y=11/16 to 15/16 (`:55–63, 249–251`), so this initial state change does not lower the collision shape. At the scheduled `UNSTABLE` tick, the state becomes `PARTIAL` and schedules another 10 ticks; `PARTIAL` lowers the leaf collision top to 13/16. At the next scheduled tick, it becomes `FULL`, whose leaf collision shape is empty (`:191–203, 55–63, 249–251`). Thus source schedules the first collision-height reduction after one 10-tick delay and removes leaf collision after a second 10-tick delay; this differs from the page’s stated 20 ticks before the first reduction. These are block-tick delays, not a claim about wall-clock duration under varying tick execution.
- **Scope of the shape:** `getCollisionShape` returns the leaf shape for the current tilt (`:249–251`). The class’s outline shape additionally includes a stem, but the collision-shape override is the player movement surface relevant here.

The source also confirms server-side tilt handling. Projectile activation sets `FULL` immediately (`:126–127`), but that non-player trigger is recorded only as context and is outside this player finding. Powered-state reset behavior is likewise not used to broaden the player timing conclusion.

## Exact source identities

Canonical ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both endpoints are ready Mojmap trees. The ready source and artifact manifest hashes were recorded for exact endpoint identity; the cited `Blocks.java` and `BigDripleafBlock.java` hashes match their source-manifest rows.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.5/mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |

Cited Java file SHA-256 values, relative to each endpoint’s `mojmap/` directory:

- `1.16.5`: `net/minecraft/world/level/block/Blocks.java` — `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`.
- `1.17.1`: `net/minecraft/world/level/block/BigDripleafBlock.java` — `1e1315b0a53eb85a3365335131e7819c1b667a55e14343d839c5211c671fd59f`; `net/minecraft/world/level/block/Blocks.java` — `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`.

This is bounded wiki corroboration, independent of other audit lanes. The timing discrepancy is suitable for independent wiki-lane review; broad pair coverage remains open.