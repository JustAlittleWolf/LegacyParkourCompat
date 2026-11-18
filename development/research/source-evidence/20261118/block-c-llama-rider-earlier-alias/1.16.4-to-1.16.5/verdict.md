# Independent audit: 1.16.4 → 1.16.5 Llama passenger Y

## Verdict

**Accepted: no source difference for the bounded operation.** For an ordinary adult player directly riding an adult default `Llama` or `TraderLlama`, the exact Mojmap source on both releases places the player with the same Y expression, gates and effective call path. No 1.16.4→1.16.5 change is supported for this operation. This verdict does not establish the release where the behavior was introduced, nor freeze the full pair inventory.

## Frozen input

- Input report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-llama-1164-1165-position-intermediate/report.md`
- SHA-256, computed directly from the report bytes with `Get-FileHash -Algorithm SHA256`: `28f4b5016dbed030c398146c2dd53a3bcec79efd8207f8f35eb098fcec5b638c`

## Exact source identity

All paths below are relative to `D:/Javastuff/LegacyParkourCompat`.

- **1.16.4 Mojmap:** `decompiled_minecraft/1.16.4/mojmap/`. Its hidden `.decompile-provenance.json` identifies the 1.16.4 client jar SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, client mappings SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, and 1.16.4 metadata SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`. This tree has no separate ready marker; I relied on the hidden provenance and per-source hashes, not a readiness-marker claim.
- **1.16.5 Mojmap:** `decompiled_minecraft/1.16.5/mojmap/`. `decompiled_minecraft/1.16.5/mojmap.ready.json` identifies `versionId: 1.16.5`, `mapping: mojmap`, `status: ready`, client SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`, and source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. The ready marker and manifest hashes match the current files.
- No version alias was used. The report's 1.16.4 source identities agree with `.decompile-provenance.json`; the 1.16.5 identities agree with `mojmap.sources.sha256`.

## Re-traced operation and scope

The relevant exact methods have identical source-file SHA-256 values on both sides (the 1.16.4 provenance and 1.16.5 source manifest agree with current file hashes):

| Source file and relevant member | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/world/entity/animal/horse/Llama.java` — `positionRider`, `getPassengersRidingOffset` | `18bf21b8d7559e4c9a3195126dc0b30b691e8e869fa1c214956a6395534e5f2d` |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` — inheritance | `2d5d0d9bbb694ec542d6d167f1e98c95a0c1991b66b6ef69c7881b326c54c8f3` |
| `net/minecraft/world/entity/player/Player.java` — `rideTick`, `getMyRidingOffset` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` — `rideTick`, `getBbHeight`, `setPos` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/client/multiplayer/ClientLevel.java` — `tickPassenger` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| `net/minecraft/client/player/LocalPlayer.java` — `rideTick` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |

The full `LocalPlayer.java` hash was independently checked even though the input report's comparison table did not list it; its identical hash is recorded in each version's source records.

Exact operation references (same line ranges in both releases):

- `Llama.java:152–164`: `positionRider` gates on `this.hasPassenger(entity)` and calls `entity.setPos(...)`. Its Y argument is `this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset()`. Addition is left-associative. `getPassengersRidingOffset()` returns `this.getBbHeight() * 0.67`; `Entity#getBbHeight()` at `Entity.java:2720–2722` returns `float`, while `0.67` is a `double` literal, so that multiplication is promoted to double. No intermediate cast is present. Body-yaw float trigonometry feeds only the X/Z coordinates.
- `Player.java:988–991`: `getMyRidingOffset()` returns `-0.35` as a double literal. Therefore the player Y passed to `setPos` is the mount Y plus the Llama float bounding height multiplied by `0.67` in double arithmetic, then plus `-0.35` in double arithmetic.
- `TraderLlama.java:22–27`: `TraderLlama extends Llama`; it has no override of either placement method. The inherited Llama behavior applies to both mount classes.
- Effective client call path: `ClientLevel#tickPassenger` (`ClientLevel.java:204–217`) calls `rideTick()` for an in-chunk passenger `Player`; `LocalPlayer#rideTick` (`LocalPlayer.java:836–845`) delegates to `Player#rideTick` (`Player.java:451–465`), whose non-stop-riding branch calls `super.rideTick()`. `Entity#rideTick` (`Entity.java:1577–1583`) calls `this.getVehicle().positionRider(this)` when the player remains a passenger. Dynamic dispatch reaches `Llama#positionRider` for either mount class.

This audit concerns only placement of the ordinary player passenger on the two named Llama classes. It does not assert placement behavior for other passenger actors, Llama/vehicle motion, baby or otherwise non-default dimensions, or other mount classes. The adult/default condition is the bounded input assumption. Because the compared source is identical, no exact intermediate introduction boundary can be inferred.
