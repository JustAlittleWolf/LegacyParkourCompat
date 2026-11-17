# Independent player-routes source audit: 26.1.2 → 26.2

Scope: audit the two named immutable coverage reports against the exact unobfuscated pair, with independent source-first review of packet-to-player writers and contact/collision/suffocation/provider routes that can reach `LocalPlayer`. This audit does not freeze the pair.

## Evidence integrity

Both source trees are ready and internally intact. I verified each ready marker's declared source-manifest, artifacts-manifest, and diagnostics hashes against the sidecars; then recomputed every Java source hash from each source ledger (6,882/6,882 for 26.1.2; 7,055/7,055 for 26.2; zero missing or mismatched entries). Exact source provenance-manifest SHA-256 values match the assignment: 26.1.2 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`; 26.2 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`.

The reports' bytes match their assigned SHA-256 identities:

- `packet_responses/coverage.md`: `8A46E462D045EF60F5F0877D67BFE0D9D5A5F74418B92109BA0E61B2761DC484`
- `player_contact/coverage.md`: `3FD892BFDE6DA7B8597C025DDD04996608044E2994CEF896F5E073ACFA48B579`

The matching source-file hashes for core routes agree with each source ledger and current file bytes. For example, `ClientPacketListener.java`: 26.1.2 `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82`, 26.2 `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`; `Entity.java`: 26.1.2 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`, 26.2 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `LocalPlayer.java`: 26.1.2 `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`, 26.2 `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`.

Method-body fingerprints below are SHA-256 over extracted source lines joined with LF (the full-file ledger hashes above are the authoritative byte-integrity check). Confirmed matching bodies include `ClientPacketListener.handleMovePlayer` (`27299b5f6d05e0c3b68385a7570bcfcea9fba140d0a879d4bb0fdd2a16501c49` both), `handleExplosion` (`19ebe7d2b1882de91ffb19e11afe707b095ff98013127365da3a051f7ca8a662` both), `handlePlayerAbilities` (`b4392da039497a0eb7522970442a543cc927b9bfc77b1ed0968c10865fa9ed43` both), `LocalPlayer.moveTowardsClosestSpace` (`97a328c492506901c2af56b034b59861d4a730ae756894b0926c6fb72b9c45eb` both), and `CollisionGetter.collidesWithSuffocatingBlock` (`da89b438c335f5e792a80a13607da3b752571edc0f84f06697ab79a508e1d762` both). `ClientPacketListener.handleUpdateAttributes` and `handleUpdateMobEffect` have differing full extracted-body fingerprints because the newer source changes Java pattern/cast syntax and local typing; inspection confirms the state-write order/gates described in the report, so byte identity should not be attributed to those two bodies.

## Verdict: packet/effect/attribute response report — ACCEPT

Bound report: exact packet/effect/attribute report bytes above.

The report's bounded route is supported by the source. I checked the listed direct client paths resolving to `minecraft.player` or entity ID and reaching player position/rotation/velocity, pose/synced state, equipment/inventory, effects, attributes, abilities, and respawn transfer. `handleMovePlayer`, `handleExplosion`, and `handlePlayerAbilities` have identical method-body fingerprints; `handleUpdateAttributes` and `handleUpdateMobEffect` retain their gates and ordered writer calls despite decompiler syntax changes. The LocalPlayer callbacks and source writer methods cited by the report also match the explained behavior. I found no omitted changed packet writer in the report's claimed bounded routes and no false no-diff claim.

The report properly marks the packet/effect inventory partial: it does not trace packet codecs/server production or claim all packet/non-packet writers, full tick coverage, or pair closure. Those are open pair-level work, not defects in this bounded report.

## Verdict: player-contact residual report — REQUEST CHANGES

Bound report: exact contact report bytes above.

The direct push, spin-contact, Shulker query/push, local escape, pose/suffocation, and target-provider comparisons are substantially source-supported. In particular, the unchanged `ClientLevel.getPushableEntities` query includes the `LocalPlayer`, and the Shulker query and `entity.move(MoverType.SHULKER, vector)` route do reach it. `LocalPlayer.moveTowardsClosestSpace` and suffocation provider method bodies match.

However, the report's NO_DIFF disposition omits a changed collision-time player velocity writer reached through `LocalPlayer` movement. `Entity.move(MoverType, Vec3)` changes in the pair: 26.1.2 collision handling has horizontal zeroing and fall-on-block update in its non-authoritative branch; 26.2 dispatches eligible simulated collisions to `restituteMovementAfterCollisions`. That new method can reverse/scale horizontal velocity and set vertical velocity from gravity compensation, drag, and restitution. Its source-body fingerprint is absent in 26.1.2 and `a333ec2b7eb3159d652cd48c3e4629df8af7f3a0dd3f2692d5f4510ae248d795` in 26.2; the enclosing `Entity.move` fingerprints differ (`e06705c103793057dd3216298fdd8f39506c217e3c398222c04f72eab62520b9` vs `8339ddca080ec0d7190e60faafc0c13a1e6a93d2a443c7b7a17849faf2dc70b8`). The 26.2 `LivingEntity.getEntityBounciness()` is also new and returns `Attributes.BOUNCINESS` (`cc05b61d61be443bc57bcd219546eaf0879c14b1c5bf9429ad0bd5b259e55fc4`); 26.1.2 has no override. These methods feed LocalPlayer's collision resolution whenever its movement clips against a bouncy block or nonzero bounciness applies. The report's sentence that general `Entity.move` collision resolution was left untouched documents the boundary, but that boundary conflicts with the assigned contact/collision audit and its broad NO_DIFF characterization. This is a source-visible player position/velocity route, not merely a vehicle/non-player response.

Please revise the report's bounded disposition to record the 26.2 restitution route and the concrete LocalPlayer reachability, or explicitly narrow the title/status to exclude movement-core collision response and leave a linked, exact open gate. Until then the report cannot support NO_DIFF for the assigned collision/contact route. No claim is made here about which historical profiles ultimately need this newer feature.

Remaining limits: neither audited report establishes full-pair coverage, intermediate patch boundaries, runtime parity, or product implementation. This audit is only a source review of the named report bytes and assigned player routes.
