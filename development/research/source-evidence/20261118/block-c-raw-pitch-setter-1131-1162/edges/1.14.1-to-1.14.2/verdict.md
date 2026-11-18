# Independent source verdict: direct local pitch response

**Bounded verdict:** No source-level movement difference was found in the ordinary direct local-player pitch response or the first active dry Elytra travel consumer between Minecraft 1.14.1 and 1.14.2. The Yarn name is `Entity.changeLookDirection(double, double)`; `Entity.turn` is a cross-mapping alias, not the symbol in these source trees. This is a bounded operation audit, not full-pair movement coverage or a freeze.

## Exact source identity and current bindings

Canonical read-only source roots are `decompiled_minecraft/1.14.1/yarn/` and `decompiled_minecraft/1.14.2/yarn/`. The nested provenance manifests have SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` (1.14.1) and `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` (1.14.2). They identify Yarn `1.14.1+build.10` and `1.14.2+build.7`, the matching intermediary mapping archives, and the exact client URLs and hashes below:

| Release | Client SHA-256 | Intermediary artifact SHA-256 | Yarn artifact SHA-256 |
| --- | --- | --- | --- |
| 1.14.1 | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` |
| 1.14.2 | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` |

The current local client and mapping archive bytes match those input hashes in the manifests. Each manifest has 3,155 source entries, matching the 3,155 current Java files in its source root. Current hashes of every relied-on source file match its manifest entry:

| Source file | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/client/Mouse.java` | `dedc0f972addc92ad335ea86210f71c4f3de56dcbebb81958cc509189b94d742` | `b3724c88b74c005d741259f89329b92b4ab3af7a3f44cd0bdf7ac0222733f243` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |
| `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` | `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947` |
| `net/minecraft/client/network/AbstractClientPlayerEntity.java` | `b220f4851a6d07eb0880c78f080a47afbcb3af0e2ea40401f9df758d97e53bc4` | `b220f4851a6d07eb0880c78f080a47afbcb3af0e2ea40401f9df758d97e53bc4` |

**Canonical-sidecar gap:** neither version root currently contains `yarn.ready.json`, `yarn.sources.sha256`, or `artifacts.sha256`. Thus there is no current root-level ready marker or separate source/artifact digest sidecar to bind. The nested provenance manifest and its per-file hashes are present and verified against current bytes, so this gap does not create a source mismatch; it remains a publication/readiness gap if root sidecars are required by the pair's acceptance record. No source generation or network lookup was performed.

## Direct ordinary local-player path

`Mouse.updateMouse()` in both releases reaches `this.client.player.changeLookDirection(j, k * n)` at line 214 on the ordinary cursor-locked, focused path when the local player exists. `ClientPlayerEntity` extends `AbstractClientPlayerEntity`, which extends `PlayerEntity`, then `LivingEntity`, then `Entity`. A search of both exact source roots finds no override of `changeLookDirection`; the sole direct call is the Mouse caller. The `Mouse.updateMouse()` method body is text-identical across this pair.

`Entity.changeLookDirection(double d, double e)` is at 1.14.1 lines 313–325 and 1.14.2 lines 315–327. Its complete extracted method text is identical across the pair (SHA-256 of the method text: `9ebf3152d324c4f300c4ea3d03f795b59ce69a1107c2453e45b1e0d877915cb7`). Both releases use the same operation sequence:

1. Compute `f = e * 0.15` and `g = d * 0.15`; both are `double` because the literal is double.
2. Update `pitch` with `(float)(this.pitch + f)`, update yaw with the corresponding cast, then clamp current pitch to `[-90.0F, 90.0F]`.
3. Independently update `prevPitch` from its own prior value using `(float)(this.prevPitch + f)`, update previous yaw, and clamp `prevPitch` to the same bounds.
4. Notify the vehicle if present.

The casts happen after double addition. Current and previous pitch are not copied from one another: each is separately accumulated and clamped. For example, with `pitch=-80.0F`, `prevPitch=80.0F`, and `e=100.0D`, `f=15.0D`; current pitch becomes `-65.0F`, while previous pitch computes `95.0F` and clamps to `90.0F`.

Finite large witness: from `pitch=prevPitch=0.0F`, `changeLookDirection(0.0D, -3000.0D)` computes finite `f=-450.0D`. Each float sum is `-450.0F` before its independent clamp, leaving current and previous pitch at `-90.0F`. This exercises the finite over-360 case without NaN, infinity, or overflow. The same input and result follow from both releases' identical method bodies.

## First active dry Elytra movement consumer

The player tick route is `ClientPlayerEntity.tickMovement()` → `PlayerEntity.tickMovement()` → `LivingEntity.tickMovement()` → `this.travel(...)`; `PlayerEntity.travel()` delegates to `super.travel(arg)` in the ordinary non-creative-flight path. In `LivingEntity.travel()`, the active Elytra branch is reached under the voluntary/logical-movement gate, outside water and lava (for a non-creative-flying player), and `isFallFlying()`. The first explicit pitch read in that branch is `this.pitch * (float)(Math.PI / 180.0)` at line 1781 in 1.14.1 and line 1787 in 1.14.2. It feeds the cosine glide factor, then the negative-pitch lift condition and sine calculation; the branch updates velocity and calls `move`.

The complete `PlayerEntity.travel(Vec3d)` method text is identical across the pair (SHA-256 `eb0a801f6dcf105ad76956fb18307c693545d81cb34e719707312cac4ee87daf`); the complete `LivingEntity.travel(Vec3d)` method text is also identical (SHA-256 `4e0112ed5d80b913a9008fd6cc12b91acca4f853a7f51df060a5274116fd9730`). Therefore the finite large witness's final `-90.0F` current pitch, rather than its pre-clamp `-450.0F` intermediate, is the value consumed by the next active dry Elytra movement call in either version.

## Conclusion

No difference was found in this assigned direct local pitch response or its first real dry active Elytra movement consumer from 1.14.1 to 1.14.2. The bounded source verdict is supported by current nested provenance and file-hash bindings. Root-level ready/source/artifact sidecars are absent, so canonical publication readiness remains an explicit open gap. This audit does not claim complete pair coverage or runtime parity.
## Phase-two binding and audit comparison

The frozen source report reviewed for this phase is `lab/c-raw-pitch-setter_1141_1142_source/report.md`, SHA-256 `11e030aab01e39028a61ef70580b28ba2d8304c9a1cd20c011c45ba862a05ae8`. Its bounded no-difference result, exact Yarn identities, nested provenance hashes, client/mapping input hashes and `Entity.java` / `LivingEntity.java` source hashes agree with this audit. Its ordinary `e=1.0D` witness agrees with the operation order; the finite `e=-3000.0D` witness above is an additional finite over-360 case. The reviewed report's additional claim that the client artifacts match `.pin.json` entries was not independently rechecked in this phase; phase one independently checked current client and mapping archive bytes against the hashes in the nested provenance manifests.

**Pitch-consumer precision:** The first pitch-dependent operation inside the active Elytra branch is `getRotationVector()` (1.14.1 `LivingEntity.java:1780`; 1.14.2 `LivingEntity.java:1786`). `Entity.getRotationVector()` forwards current `this.pitch` and yaw into the rotation-vector calculation (1.14.1 `Entity.java:1635–1636`; 1.14.2 `Entity.java:1637–1638`). The branch then explicitly reads pitch into the radians local `l` (1.14.1 `LivingEntity.java:1781`; 1.14.2 `LivingEntity.java:1787`), which feeds its cosine and negative-pitch sine/lift math. The earlier phrase “first explicit pitch read” refers to this radians local; it does not exclude the preceding rotation-vector read. The full `LivingEntity.travel(Vec3d)` methods remain text-identical across this pair.

**Publication status:** The reviewed report and nested `.decompile-provenance.json` files provide bounded, hidden source provenance: exact mapped input identities plus per-source-file hashes checked against the current source tree. This independently supports the operation-level source verdict. It is distinct from current root-level publication readiness. As recorded above, neither canonical version root has `yarn.ready.json`, `yarn.sources.sha256`, or `artifacts.sha256`; the frozen report itself does not establish those missing bindings. Treat canonical ready-sidecar publication as open even though the bounded source and provenance checks pass. This remains a bounded no-difference result, not complete pair coverage or runtime parity.

Phase-two audit attestation: I verified the frozen report against the supplied SHA-256, compared its source and operation claims with this verdict, and appended this section without changing the original verdict prefix. The original prefix SHA-256 is `69eb97ad529d7bad5820a880530c16ee2ea4fcdc0dad5d07e7b125812c849556`.