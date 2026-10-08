# F-S2-01: 1.21.4 centers the scaled Shulker peek-push query

- Status: source-confirmed for the exact 1.21.3 → 1.21.4 pair; finding snapshot not submitted.
- Inventory: `INV-COLLISION`, `INV-STATE`, `INV-MODIFIERS`, `INV-EXTERNAL`.
- Scope: a Shulker whose synchronized `Attributes.SCALE` is not `1.0F`, while its peek amount increases. The Shulker is not carrying the local player, and the player's collision box intersects the symmetric difference between the two query regions.

## Source difference

In A, `Shulker.onPeekAmountChange()` queries `getProgressDeltaAabb(...)` and translates that box by `(getX() - 0.5, getY(), getZ() - 0.5)`. The helper begins with local bounds `[0, scale]` on X, Y and Z, then expands and contracts them according to the attachment face and previous/current peek values.

In B, the call passes `this.position()` to an overload that begins with centered X/Z bounds `[-scale/2, scale/2]`, applies the same expansion and contraction expressions, and translates the result by the entity position.

For an upward-attached Shulker at position `(x, y, z)` with scale `2.0F`, the UP direction leaves the query's X/Z intervals unchanged by expansion and contraction. A queries X and Z over `[x - 0.5, x + 1.5]`; B queries over `[x - 1.0, x + 1.0]`. These regions differ by a half block on each side. A player in the A-only fringe is selected and moved upward in A, but is not selected by this query in B. The direct displacement remains `peekDelta * scale` along the face-opposite direction when the player is selected.

## Reachability and player effect

`Attributes.SCALE` is a syncable living-entity attribute with default `1.0` and range `0.0625` to `16.0`; `2.0F` is within that range. Both clients' `handleUpdateAttributes()` resolve the target living entity and apply the packet's base value and transient modifiers to its attribute instance. The Shulker type's registered attributes include the inherited LivingEntity attributes, so the scale read by `getScale()` can differ from the default on the client.

The paired Shulker tick calls `updatePeekAmount()` and then `onPeekAmountChange()` when the amount changes. During an opening step, the method searches entities in the version-specific query region, excludes spectators and passengers of the same vehicle, then directly calls `Entity.move(MoverType.SHULKER, displacement)` on every non-Shulker entity with physics enabled. The local player is therefore a reachable movement consumer when it occupies the differing fringe.

This finding is limited to the direct player-push query and the stated non-default-scale/opening conditions. The separate Shulker entity collision AABB arithmetic at non-default scale and broader attribute/modifier producers remain open in `DEP-SHULKER-SCALE` and `DEP-MODIFIERS`.

## Exact source evidence

- A `Shulker.java`: `tick()` 170-178; `onPeekAmountChange()` 226-242; `getProgressDeltaAabb()` 250-255; SHA-256 `d4103fb6f4ee4f4615ae4bcf4094f14f61821c2c715aaf5d2b2721390c41e4de`.
- B `Shulker.java`: `tick()` 170-178; `onPeekAmountChange()` 225-241; `getProgressDeltaAabb(..., Vec3)` 249-256; SHA-256 `6da89fa5d298c1913ed832aacc6fa19591a13b4be5586d3dbe5085f33457a5d2`.
- A `ClientPacketListener#handleUpdateAttributes()`, 2156-2180, SHA-256 `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a`; B, 2162-2186, SHA-256 `eef170dd22b5711e7d9527601592093a0456590f192b54fefef170b5791165b4`.
- `Attributes.SCALE`, 72-74, both versions SHA-256 `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`.
- `LivingEntity#getScale()` reads the SCALE attribute in both versions at 541-544; source hashes A `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72881cfc52`, B `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
- All cited Java hashes were checked against the exact-version Mojmap source manifests. Evidence is source-only; no runtime outcome is claimed.
