# Independent review: 1.17.1 boat passenger yaw correction

**Decision: ACCEPT**
**Review date:** 2026-10-08
**Review branch:** `fix/review-passenger-yaw-remount-2026-10-08`
**Review base (`main`):** `ea812dc9171fdf41e02b3a2292db7d40b105c9c4`
**Reviewed branch:** `feat/movement-passenger-yaw-2026-10-08`
**Reviewed tip / latest main merge:** `32c8b0a7e089e32d2b755a513241ac0e58d1d562`
**Correction commit:** `5c2cc08f6b8eaccb02f89a80de4823a33ac37503`

## Scope and evidence

The reviewed net diff is `main..32c8b0a7e089e32d2b755a513241ac0e58d1d562`: 7 files, 193 insertions, 0 deletions. `git diff --check` is clean. The review covered the mixin, hook, 1.17.1 change and provider registration, mixin configuration, implementation record, and owner handoff:

- `src/client/java/me/wolfii/legacyparkourcompat/mixin/ClientPacketListenerMixin.java`
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_17_1/BoatPassengerYawRefresh.java`
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_17_1/MovementChanges.java`
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/BoatPassengerYawRefreshBehavior.java`
- `src/main/resources/legacyparkourcompat.mixins.json`
- `workflows/fix-implementation/runs/1.17.1-boat-passenger-yaw-refresh.md`
- `workflows/orchestration-2026-10-08/handoffs/01a11af4-3356-78d2-8357-0dfeff91a494.md`

The owner handoff and implementation record were located and checked. The accepted finding is `064fc24e5e3d8b4dab4f12c2000dcb13d37514ca`, with SHA-256 `3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95`; the blind review is `4ff925e4e4186adb704d57d9b3471c559945436c`. The previous corrected review is `bdb6443`.

Historical references are the frozen 1.17.1 and 1.18.2 Mojmap sources recorded in the accepted finding. Current references are the exact 26.2 unobfuscated source files in `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated`: `ClientPacketListener.java` (SHA-256 `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`) and `LocalPlayer.java` (SHA-256 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`).

## Source-to-code result

In 26.2, `ClientPacketListener#handleSetEntityPassengersPacket` first calls `PacketUtils.ensureRunningOnSameThread`, looks up the packet vehicle, records `vehicle.hasIndirectPassenger(minecraft.player)`, ejects passengers, and then invokes `passenger.startRiding(vehicle, true, false)` for each resolved passenger. When the local player was not previously mounted, the handler itself writes boat yaw. For an already mounted player it does not, leaving that update to the historical `LocalPlayer#startRiding` behavior.

The mixin targets `ClientPacketListener#handleSetEntityPassengersPacket(ClientboundSetPassengersPacket): void` and wraps the exact invocation descriptor `Entity.startRiding(Entity, boolean, boolean): boolean` (`Lnet/minecraft/world/entity/Entity;startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z`); the wrapper receiver is the passenger. It marks success only after the original call returns true and only when that receiver is the current local player and its target is the exact captured `Boat`. The existing indirect-membership check before ejection is retained, so a nested passenger can qualify before the packet changes the player to a direct passenger. TAIL copies and clears all captured state before dispatch. The change then writes only current `yRot`, and only when the selected profile equals `V1_17_1`.

The 1.17.1 source confirms its successful `LocalPlayer#startRiding` path writes boat yaw; 1.18.2 no longer has that boat-specific yaw write. No render-field restoration is required for the accepted movement consequence. `ChangeResolver` returns no changes for `CURRENT`; its nearest-applicable rule and the exact `V1_17_1` guard keep this delta out of native 26.2 and later historical profiles. `Boat` capture excludes the modern-only `Raft` subtype of `AbstractBoat`, consistent with the project rule not to add old behavior to modern-only features.

## Required case checks

| Case | Static result |
|---|---|
| Nested → nested | No direct local-player `startRiding` call succeeds; success flag stays false, so no reset. |
| Nested → direct | Pre-packet indirect membership is true; successful player-to-captured-boat call sets the flag; TAIL applies the 1.17.1 yaw. |
| Direct → direct | Same successful direct-remount path; historical yaw is applied once. |
| Player removed from packet list | No successful local-player remount is observed; no reset. |
| Failed mount | Wrapper returns false unchanged and does not set success; no reset. |
| Initial direct mount | Pre-packet membership is false, so hook does not run; current 26.2 handler performs its native initial boat-yaw write. |
| Unknown vehicle / early thread reschedule | No refresh is captured for an unknown vehicle. HEAD clears state before the thread check, so a rescheduled early return cannot leave stale captured state; main-thread re-entry clears it again. |
| Default `CURRENT` | Resolver supplies no historical behavior; the wrapper only captures and clears state, and native refresh yaw remains unchanged. |

## Six review angles

1. **Requirement fit — PASS.** The change restores only the accepted 1.17.1 passenger-refresh movement yaw consequence and retains nested-to-direct transitions.
2. **Correctness and edge cases — PASS.** The event gate requires a successful remount of the exact local player to the exact captured boat. State is cleared at method entry and before tail dispatch.
3. **Coverage — PASS.** All requested packet membership, successful/failed mount, initial mount, early-return, and `CURRENT` cases above were traced through source and gates. No runtime claim is made.
4. **Conventions and duplication — PASS.** The behavior is a granular registered change behind a thin shared client hook; no movement loop or vanilla class is duplicated.
5. **Permissions and visibility — PASS.** The injection is client-only and targets the client packet listener. The mechanic is registered through the existing versioned provider/resolver path.
6. **Security and error handling — PASS.** No external input is trusted beyond resolved in-world entities and successful vanilla mount results; no errors are swallowed and no new resource or network behavior is introduced.

## Findings and verification limits

**Actionable findings: 0.** I found no confirmed or probable issue at severity low or higher in the reviewed net diff.

This was a source-static review. No build, tests, game client, TAS, server, or Docker runtime was run, as requested. The review establishes source-path and guard behavior; it does not claim runtime parity.
