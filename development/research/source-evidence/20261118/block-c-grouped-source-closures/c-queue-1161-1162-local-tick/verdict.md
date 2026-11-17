# Independent source audit verdict: 1.16.1 to 1.16.2 local-player tick slice

Terminal status: COMPLETED
Verdict: ACCEPTED for the assigned grouped no-difference ledger, with the bounded limits below.

## Immutable draft binding

- Draft: c-queue-1161-1162-local-tick/report.md
- Exact draft SHA-256: a29bcaf304ac302d76a61bfacabfc54ac3deb5c7498e4dc32763f39f8c83edba
- This digest identifies the report bytes, not a Minecraft source body. The report is a summary and is not byte-identical to any source file.

## Evidence identity and integrity

Read-only source roots: decompiled_minecraft/1.16.1/mojmap/ and decompiled_minecraft/1.16.2/mojmap/.

The nested ready records identify exact versions 1.16.1 and 1.16.2, mapping mojmap, and ready status. Their source-manifest hashes match the actual manifest bytes: older 76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754; newer 10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7. The corresponding outer provenance records name those same version/mapping pairs and the shared exact-pair run. The source-manifest, artifact-manifest and movement-diagnostics hashes also match each nested ready record. For both releases, the actual hashes of LocalPlayer.java, Player.java, LivingEntity.java, Entity.java, and EntityDimensions.java match their entries in the respective source manifest.

## Bounded claim audit

- LocalPlayer.tick(), sendPosition(), and setSprinting(boolean) have identical method bodies across the pair. Player.tick(), Player.aiStep(), Player.jumpFromGround(), Player.travel(Vec3), LivingEntity.tick(), LivingEntity.aiStep(), LivingEntity.jumpFromGround(), and LivingEntity.travel(Vec3) likewise have identical method bodies. The source body hashes were compared directly; line shifts do not affect that result.
- LocalPlayer.aiStep() is not wholly identical. After masking only its assignment-excluded in-block response conditional, the remaining method bodies are identical (SHA-256 93942fb9a880ee4ee6d065505895eaa6619da752fc7818e0a848eddef4db407c on both sides). Thus the sprint timers/gates and the assigned jump timer/flag gates support the draft's no-difference claim. The in-block response itself is excluded and was not evaluated for movement eligibility.
- Entity.setPos(double,double,double) is not identical. Both versions first call setPosRaw with the same coordinates. The older body then computes float width / 2.0F, copies float height, and constructs the AABB with the same ordered coordinate expressions used by the newer EntityDimensions.makeBoundingBox(double,double,double) helper. The newer helper contains exactly those operations and no entity-state writes. The changed delegation therefore preserves the assigned position and box observables for this relocation path; it is not source-body identity.

These operations are reachable from the local player's tick/position path, and the audited outputs are player movement control state and position/bounding-box state. The draft lists the suffocation/in-block and liquid-state edits as exclusions; this audit does not validate their source behavior and accepts neither as a movement finding.

## Limits

Acceptance covers only the named grouped slices and this exact endpoint comparison. It does not establish full 1.16.1-to-1.16.2 tick/inventory coverage, close dependencies outside this assignment, prove an intermediate patch boundary, or establish runtime parity. Input gathering, pose/dimension providers and consumers, and other direct responses remain unexamined as the draft states.