# Independent audit: early player-push reciprocal path

Status: COMPLETED

## Assignment and evidence boundary

Windows host, PowerShell execution. Reviewed the supplied immutable source-audit notes without opening product sources/history, wiki inventories, backlog, tests, builds or game processes. The supplied notes match their required SHA-256 identities:

- `c-external-early-push-reciprocal/report.md`: `DACB37158C5E029E8E1420D38FFD6F8BE447A02D3E6BEB090F9C828500AF660B`
- `c-external-early-push-reciprocal/1.8.9-caller-gate-supplement.md`: `0A84F93E312E4156B435953A5BEC3CB188A70705EBE740491BCD59609E9168C1`

Retraced the exact mapped canonical sources under `decompiled_minecraft/`. The 1.8.9, 1.9 and 26.2 identities and file hashes listed in the supplied notes match local source files. This audit is bounded to the ordinary LivingEntity collision dispatch, the Entity push writer and the possibility of other native client calls with both a LocalPlayer receiver and a LivingEntity argument.

## Verdict

The supplied ordinary-path account is independently confirmed:

- **1.8.9**: `LivingEntity.tick` (Ornithe Feather `net/minecraft/entity/living/LivingEntity.java:1452-1455`) invokes `pushAwayCollidingEntities()` only inside `if (!this.world.isClient)`. The candidate query and `entity.push(this)` dispatch are downstream at `:1463-1480`. Thus an ordinary logical-client living tick skips the complete helper, query, receiver callback and reciprocal writer; neither side is written by this path.
- **1.9**: `LivingEntity.tick` unconditionally calls its push helper (`LivingEntity.java:1700-1702`); the query and dispatch are `:1729-1741`. The client `EntityFilter.canBePushedBy` admits a local player candidate (with spectator/pushability and collision-team gates; `EntityFilter.java:55-92`). For an intersecting candidate, the argument is the ticking LivingEntity and the `Entity.push` receiver is the local player. `PlayerEntity.push` skips sleeping players before calling the base writer (`PlayerEntity.java:1879-1885`). `Entity.push` then independently gates its two velocity writes (`Entity.java:1057-1088`): local receiver write is skipped when the receiver has passengers; reciprocal argument write is skipped when the argument has passengers. Same-vehicle/no-clip/threshold gates precede both writes. Therefore a qualifying ordinary client collision writes both sides; the reciprocal target can be a mob or a remote player copy.
- **26.2**: `LivingEntity.tick` reaches `pushEntities()` with no client guard (`world/entity/LivingEntity.java:3167-3174`); common loop dispatch is `doPush` / `entity.push(this)` (`:3232-3253, 3281-3283`). `ClientLevel.getPushableEntities` returns only the local player when distinct from the pusher, intersecting, and selected by `EntitySelector.pushableBy` (`client/multiplayer/ClientLevel.java:540-544`; selector gates at `world/entity/EntitySelector.java:29-58`). `Entity.push(Entity)` retains separate receiver and argument gates (`world/entity/Entity.java:1876-1905`), so its ordinary collision call can write both sides. The current gates differ from 1.9 (`isVehicle` plus `isPushable` per endpoint); vector writes filter non-finite values and mark `needsSync` (`:1907-1917`).

## Caller-shape audit

Recipient types alone do **not** define the bounded ordinary caller: the local-player receiver plus living argument is a role shape, not a unique callsite identity. I checked the exact source callsites of `Entity.push(Entity)` in the relevant entity trees and traced the plausible additional living-argument call:

- In 1.8.9, other explicit callsites are boat, minecart and armor-stand collision dispatch (`BoatEntity.java:353`, `MinecartEntity.java:297`, `ArmorStandEntity.java:266`); each passes that vehicle/stand as the argument. 1.9 has the corresponding boat, minecart and armor-stand shapes (`BoatEntity.java:273`, `MinecartEntity.java:305`, `ArmorStandEntity.java:298`); arguments are respectively a BoatEntity, MinecartEntity or ArmorStandEntity. The 1.9 minecart push override is also server-gated (`MinecartEntity.java:641-649`). None of these is another client caller with a LivingEntity argument and local-player receiver.
- In 26.2, ArmorStand's override dispatches only ridable minecart candidates with the ArmorStand as argument (`decoration/ArmorStand.java:174-184`). Vehicle callsites pass vehicle arguments. Ravager's `defender.push(this)` is the apparent second LivingEntity-argument call (`monster/Ravager.java:208-219`), but it is invoked through `LivingEntity.blockUsingItem(ServerLevel, ...)` (`world/entity/LivingEntity.java:1378-1379`, called at `:1337`); it is server-level damage handling, not a native client site. Ravager's client roar uses vector `push(x,y,z)`, a distinct overload, not `push(Entity)` (`monster/Ravager.java:250-253`).

Accordingly, within this exact source pair, I found **no additional concrete native-client `Entity.push(Entity)` site** with both a LocalPlayer receiver and a LivingEntity argument besides ordinary LivingEntity collision dispatch. Other entity/caller combinations are concrete and already distinct, but do not meet both role constraints. Do not broaden this bounded inventory on a merely theoretical possibility that another runtime caller could supply those types.

## Acceptance boundary

The independent audit confirms the caller gates, recipient-role mapping, and paired reciprocal writes described in the immutable notes. It does not establish full 1.8.9–1.9 release-pair coverage, inspect intermediate versions, choose whether out-of-scope reciprocal remote/non-player writes should be suppressed, or establish runtime parity or a product hook seam. No blocker.


