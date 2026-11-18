# Independent audit: Llama rider Y placement, 1.18.2–1.20.1

TERMINAL: **COMPLETED**

## Verdict

**ACCEPT, bounded claim.** The candidate report's ordinary adult Llama/TraderLlama passenger Y-placement claim is supported by the exact local Mojmap source inspected for every release in the assigned range: 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, and 1.20.1. I found no source-visible Y-placement change in that interval. This is acceptance of this narrow claim only; it does not establish full-pair coverage, independent pair freeze, or runtime parity.

For 1.18.2 through 1.19.4, `Llama.positionRider(Entity)` guards on `hasPassenger` and invokes the position writer with Y ordered as `this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset()`. In 1.20 and 1.20.1 the writer is `MoveFunction.accept(Entity, x, y, z)` under the same guard, with the same Y argument and order. Every inspected Llama override returns `getBbHeight() * 0.6` (double literal); `Player.getMyRidingOffset()` returns `-0.35` (double). Each inspected Llama and TraderLlama registration has dimensions `.sized(0.9F, 1.87F)`. At ordinary adult default dimensions and scale, the Y expression therefore evaluates to `llamaY + (double)1.87F * 0.6 + (-0.35)`, approximately `llamaY + 0.772000002861023`.

The direct 1.20 source confirms the callback API transition and does not reveal a placement change. The report's 1.20.2 fence is also source-supported: the Llama passenger attachment Y is `dimensions.height - (isBaby() ? 0.8125F : 0.5F) * scale`, and the Llama-specific old `positionRider` override is gone. This observation only identifies a distinct native API beyond the assigned endpoint; no later mechanics or other mount carriers were examined for this verdict.

TraderLlama behavior is inherited from Llama; `LocalPlayer` inherits through `AbstractClientPlayer` from `Player`, so the player rider offset applies. No claim is made about other passenger carriers, horizontal placement, yaw, steering, general riding behavior, or unsupplied intermediate patch releases. The assigned listed stable snapshots are inspected directly; endpoint comparison alone is not used to interpolate behavior.

## Evidence checked

All source paths below are under `decompiled_minecraft/<release>/mojmap/`.

- For each of 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, and 1.20.1: `net/minecraft/world/entity/animal/horse/Llama.java` placement guard/writer and riding offset; `net/minecraft/world/entity/player/Player.java` riding offset; `net/minecraft/world/entity/EntityType.java` Llama and TraderLlama dimensions. The focused source bodies match the report's stated expressions and values.
- For 1.18.2 and 1.20.1, `client/player/LocalPlayer.java` and `client/player/AbstractClientPlayer.java` confirm the inheritance path described in the report.
- For the 1.20.2 boundary, `animal/horse/Llama.java`, `player/Player.java`, `Entity.java`, and `LivingEntity.java` confirm the attachment-point model and generic passenger position dispatch summarized above.
- The supplied candidate report identity was verified before review: `report.md` SHA-256 `7c01b54e8bc570d2b80fc91a5e90fa2d0a7d623c24cafb7047e2cc3975cd638b`.

## Provenance limits

The ready-marked snapshots for 1.18.2, 1.19 through 1.19.4, 1.20.1, and 1.20.2 carry local Mojmap version/mapping metadata and source-manifest digests. The candidate reports selected-file hashes checked against those manifests; I confirmed the exact source bodies and ready marker identities for the endpoint/fence. The 1.20 source is nested legacy output with no ready marker or standalone source manifest. Its `.decompile-provenance.json` SHA-256 is `573f0bb3e18e39ae882769a007a1c97d711da123034f9a1c894eb407ef58030f`; this is weaker release pinning, as the candidate report correctly discloses. The 1.20 focused bodies were directly inspected and are byte-identical to the corresponding 1.20.1 placement sources, but that does not strengthen the legacy tree's version association beyond its local provenance. These findings support only the named local snapshots and must not be generalized to uninspected patches.

## Immutable input and output

Candidate input SHA-256: `7c01b54e8bc570d2b80fc91a5e90fa2d0a7d623c24cafb7047e2cc3975cd638b`.

Audit output: `verdict.md` in `c-queue-1182-1201-llama-y-alias-audit/`; its SHA-256 is reported in the terminal handoff after writing.