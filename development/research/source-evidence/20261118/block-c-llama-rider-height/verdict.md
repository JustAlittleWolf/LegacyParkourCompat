# Independent source audit: llama rider height

TERMINAL: **COMPLETED**

Verdict: **ACCEPTED for the bounded local-player Y-placement claim, with a later-operation fence at 1.20.2.** This is an operation-level source audit, not a full-pair coverage freeze or a runtime-parity verdict.

## Bound input and revision

- Draft: `llama-rider-height-source/comparison.md`
- Draft SHA-256: `2f9c33ea502b8d27f7c497d4cb01827a524caa04860f7942714fba877f6bfc2e`
- Scope: exact local client placement of a player passenger on Llama/TraderLlama, the proposed 1.17.1-to-1.18 stable change, reachable player witness, and source-supported later operation boundary.

## Rechecked source claims

All four draft releases (1.17.1, 1.18, 1.18.1, 1.18.2) have `ready` Mojmap markers whose version and namespace match the directory. The local `mojmap.sources.sha256` digest matches the marker, and each checked source-file digest matches its manifest entry. The checked files include `Llama.java`, `Entity.java`, `Player.java`, `ClientLevel.java`, `LocalPlayer.java`, `EntityType.java`, `AbstractChestedHorse.java`, and `AbstractHorse.java`.

- In 1.17.1 `Llama.java`, `positionRider` checks `hasPassenger` and passes `getY() + getPassengersRidingOffset() + entity.getMyRidingOffset()` to `setPos` (lines 154-160); the override returns `getBbHeight() * 0.67` (163-165).
- In 1.18, the same guarded writer and addition order remain (156-162), while the override is `getBbHeight() * 0.6` (165-167). 1.18.1 and 1.18.2 retain the same `Llama.java` source hash as 1.18. The next stable release carrying the new literal in this checked boundary is **1.18**.
- `getBbHeight()` is a float and the unsuffixed factors are double literals, so the multiplication is performed in double after float promotion. `Player.getMyRidingOffset()` returns `-0.35`; the default llama dimensions are `0.9F, 1.87F`. For an adult/default llama passenger, the changed term lowers the written Y by about 0.1309 blocks. This delta is based on source arithmetic, not a simulated trajectory.
- On the logical client, `ClientLevel.tickPassenger` accepts a `Player` passenger and calls `rideTick`; `Entity.rideTick` ticks the rider and, if it remains a passenger, calls `getVehicle().positionRider(this)`. Dynamic dispatch reaches the llama writer and its `hasPassenger` guard. `AbstractChestedHorse.mobInteract` reaches `doPlayerRide` on the non-baby path; `AbstractHorse.doPlayerRide` starts the ride server-side. A normal adult Llama is therefore a reachable witness once the passenger link is synchronized. TraderLlama inherits the Llama placement override.

The claim concerns the passenger's direct Y position writer. It does not change the llama's own travel, player input/control response, or other rideable families; those operations/families were not audited as part of this slice.

## Later source boundary

The Llama-specific `getPassengersRidingOffset() == getBbHeight() * 0.6` design is present in exact 1.20.1 source. In 1.20.2, `Llama` instead overrides `getPassengerAttachmentPoint`, returning a vector whose Y is `dimensions.height - (isBaby() ? 0.8125F : 0.5F) * scale`. `Entity` now obtains passenger position through `getPassengerRidingPosition` and adds the passenger's riding offset in its position writer. The checked 1.20.1/1.20.2 source manifests and selected-file hashes validate locally. Thus 1.20.2 is a source-supported fence for the old Llama riding-offset hook; applying that hook through 1.20.2 would target a different placement design. In 1.20.5 the Llama override delegates through registered passenger attachments, another source-visible design change. This audit does not assert behavior equivalence across those later APIs or prescribe runtime resolver behavior.

## Provenance limits and remaining scope

The canonical source trees and their file hashes are intact for the checked releases. The 1.17.1 and 1.18.2 batch provenance records do not carry per-version raw client/mapping artifact hashes; the local ready marker does carry the client hash. The later 1.20.1/1.20.2 provenance records identify exact run/version and source-manifest digest, but do not include a mapping-artifact hash. I did not regenerate any decompilation or independently re-hash/download raw client or mapping artifacts. No intermediate 1.17.x prereleases, snapshots, or full tick-graph/family inventory were checked. The evidence supports the stable 1.17.1-to-1.18 difference and the 1.20.2 operation fence only; it does not assign an earlier prerelease cutover or close the complete release-pair inventory.

## Audit artifact identity

- Input draft SHA-256: `2f9c33ea502b8d27f7c497d4cb01827a524caa04860f7942714fba877f6bfc2e`
- Verdict SHA-256: computed after writing this file; recorded in the completion message.