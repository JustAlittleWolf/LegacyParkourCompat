# Independent source audit: 1.14.4 → 1.15 position correction

## Assignment and evidence identity

Audited `.native-source/20261028/frontier-c/1144_1150-position-correction-closure/MOJMAP-PAIR-SUPPLEMENT.md` at SHA-256 `f22c87e155afe7284a01662c6488a5636fbdcdd38a29f9e0874ad0a2a4748f53`. The supplied digest matches the file.

The source-only comparison uses `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` and `...\1.15\mojmap`. For 1.14.4, `mojmap.ready.json` identifies release 1.14.4, Mojmap, 3,250 files / 17,080,207 bytes, client digest `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`, and manifest digest `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. Its `mojmap.provenance.json` identifies the `feather,mojmap` preparation and its Feather inputs. The parent artifact manifest digest matches the ready marker; its rows include those Feather artifacts and the same client digest. For 1.15, the ready marker and embedded `mojmap.provenance.json` agree on version, Mojmap namespace, count, bytes and source manifest digest. The provenance identifies exact version metadata `1.15`, client digest `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`, official client mapping digest `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`, and batch `1.15,1.15.1`; the parent artifact manifest digest and rows agree.

I recomputed each source-manifest file digest, row count and summed source bytes. Both complete manifests validate with zero missing or mismatched rows: 1.14.4 is 3,250 rows / 17,080,207 bytes; 1.15 is 3,329 rows / 17,522,461 bytes. The packet, listener, Entity, LocalPlayer, LivingEntity and Player file hashes listed in the supplement all match their manifest rows and recomputed SHA-256 values.

## Source verdict

**Finite-input final position and velocity match for this handler path.** The packet class is byte-identical across the two Mojmap roots (SHA-256 `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d`). Its encoding, relative-axis masks and dispatch agree. In `ClientPacketListener.handleMovePlayer`, each absolute position axis assigns its coordinate/history and zeros that velocity component; each relative axis retains velocity and adds the packet delta to current and old position. The sums that use current position are written with reversed operand order across releases (`d += player.x` versus `player.getX() + packet.getX()`); for finite values those additions produce the same result. The old-position sums retain the same operand order.

The handler’s intermediate writes differ in order: 1.15 calls `setPosRaw` and writes `xo/yo/zo` before setting velocity and calling `absMoveTo`; 1.14.4 reaches position and history writes through `absMoveTo`. In both, `absMoveTo` clamps X/Z to ±3.0E7, leaves Y unclamped, sets `xo/yo/zo` from the resulting position, updates position and bounding box, and leaves the selected velocity untouched. Both delta setters store the selected vector without normalization. No source call between the intermediate writes consumes them. Thus final current coordinates, bounding box, position history, and velocity agree for finite packet and starting player state. The response packet uses equivalent final coordinates after `setPos` (the bounding-box minimum Y equals player Y); this is a source-value observation, not a scheduling claim.

**Yaw history differs for some finite inputs, then is reset before movement on the next gated LocalPlayer tick.** In 1.14.4 `Entity.absMoveTo` assigns raw `g` to both `yRot` and `yRotO`, computes their difference as zero, then `setRot` wraps only current yaw with `% 360.0F`. In 1.15 it wraps current yaw first and copies that result into `yRotO`. Therefore the old-yaw fields differ exactly when modulo changes the input (for example `360.0F` gives old yaw 360 in 1.14.4 and 0 in 1.15). The supplement’s phrase “outside the normalized interval” should be replaced with this exact condition: `181.0F` is outside the common [-180, 180] convention but `% 360.0F` leaves it 181, so it does not exhibit the old-yaw difference. Current yaw agrees across the versions; pitch agrees because both clamp to [-90, 90] before its modulo operation.

On the next `LocalPlayer.tick`, the body runs only if `level.hasChunkAt(...)` passes. When it passes, `Player.tick` reaches `LivingEntity.tick`, which calls `Entity.tick` / `baseTick`; `baseTick` copies current yaw and pitch into their old fields before `LivingEntity.tick` reaches `aiStep` and before LocalPlayer’s later send-position path. Thus the correction’s old-yaw discrepancy is overwritten before movement in that gated tick body. If the chunk gate fails, the body and movement path are skipped, so the unconditional wording “the next tick resets” needs that gate qualification. No runtime scheduling or trajectory claim follows from this source order.

The first-session claim also checks out: 1.14.4’s `!started` block repeats the `xo/yo/zo` values already installed by `absMoveTo`; 1.15 omits those duplicate writes. The final position history is the same.

## Scope caveat retained

The original `POSITION-CORRECTION.md` is still blocked for its assigned **1.14.4 Yarn → 1.15 Mojmap** identity: the canonical 1.14.4 tree has no published Yarn root or identity metadata. This Mojmap → Mojmap supplement establishes only the paired slice stated above and does not resolve that Yarn blocker, establish whole-pair coverage, or support claims about unrelated correction ingress, runtime scheduling, or trajectories.

## References checked

- `1.14.4/mojmap/net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java:9-128`; same file/range in 1.15.
- `ClientPacketListener.java:659-714` (1.14.4) and `:669-734` (1.15).
- `Entity.java:305-307, 1116-1139, 2675-2681` (1.14.4); `:1094-1105, 2680-2686, 2728-2732` (1.15).
- `LocalPlayer.java:178-195` (1.14.4) and `:179-196` (1.15); `Player.java:209-237` / `:210-238`; `LivingEntity.java:1975-2042` / `:2024-2103`; `Entity.java:333-357` / `:340-361`.
