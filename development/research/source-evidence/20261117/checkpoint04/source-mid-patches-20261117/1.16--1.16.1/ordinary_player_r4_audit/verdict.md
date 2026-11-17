# Independent source audit verdict

Verdict: ACCEPT for the bounded r4 source correction.

Bound report: `build/source-mid-patches-20261117/1.16--1.16.1/ordinary_player_revision_r4/coverage-r4.md`
Bound report SHA-256: `4cc8599cb3ab2155923cc35a1e1bf62e1d18e36834cd8bcf71ab5a4bdd269440`

The added moving-piston route is supported by exact Mojmap source. `PistonMovingBlockEntity.tick()` calls `moveCollidedEntities(f)` while progress is below 1.0 (`:265-290`). In `moveCollidedEntities(float)` (`:101-134`), the method queries entities overlapping the moving collision shape; for entities whose piston push reaction is not `IGNORE`, the slime-block branch writes the movement-axis velocity component to the direction step while preserving the other components (`:110-130`). For a `ServerPlayer`, it then sends `ClientboundSetEntityMotionPacket(entity)` directly (`:131-133`). The exact `PistonMovingBlockEntity.java` bytes hash to `c65374aa5b38bb5c34aefd652ce26aa44beb391f1dedd6d57d87f7e33882b89c` at both 1.16 and 1.16.1, matching both declared source-manifest entries. `ServerPlayer.java` is also byte-identical with manifest-bound SHA-256 `f457e72f9113d30cba3261ad0e77032fb56a3c08c6543535e4d1c774b230fdf6`; the motion packet class remains bound to the prior audit's verified identical hash `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762`.

This confirms the omitted direct server response and transport path. The previously audited client route remains unchanged: the client registers its local player under the server entity ID, `handleSetEntityMotion` resolves the entity without a player exclusion, and `Entity.lerpMotion` writes its velocity. The prior r3 audit verified that route and the full pair census; this review reused those unchanged checks and did not rehash the source trees. Upstream piston activation/state production remains outside the stated boundary; the direct player response and local-player packet consumer are included as movement response.

The report title still says “revision 2” although the bound file is r4. This is editorial metadata only and does not alter the accepted claim/evidence; bind any wording erratum separately without reopening this technical verdict.

This verdict accepts only the bounded source correction. Packaged resource/tag membership, broader dependencies, full-pair coverage, runtime behavior, and pair freeze remain open. No onset or runtime parity conclusion is established.