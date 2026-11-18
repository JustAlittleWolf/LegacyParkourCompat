# Independent source verdict: 1.14.1 → 1.14.2 Yarn

## Frozen before assigned report review

This source-only review was completed before opening the assigned author report. The exact canonical sources are the primary checkout's read-only `decompiled_minecraft/<version>/yarn` trees. Their `.decompile-provenance.json` manifests are intact and each relevant source hash below matches the manifest.

| Side | Decompiled source publication identity | Client SHA-256 | Yarn mapping pin | Intermediary mapping pin | Provenance manifest SHA-256 |
|---|---|---|---|---|---|
| 1.14.1 | `decompiled_minecraft/1.14.1/yarn`; pipeline `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess` | `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` (`.../55ba86ddcbc3579397f41910463ffd4056e1e523/client.jar`) | Yarn 1.14.1+build.10; `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | Intermediary 1.14.1-v2; `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |
| 1.14.2 | `decompiled_minecraft/1.14.2/yarn`; same pipeline identity | `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` (`.../ca6c5a139045967229975c0c0b7f93e78b4314c2/client.jar`) | Yarn 1.14.2+build.7; `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | Intermediary 1.14.2-v2; `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |

Manifest-pinned Minecraft metadata identities are 1.14.1 package SHA-256 `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` (package `183e49ba0fabb5ef8a304f8f5907fa887f40c466`) and 1.14.2 package SHA-256 `237aa8d499b71bb17122cbee40b8748efcd5ac9d8362c68c89e99ab0703e66b8` (package `83299b3bf3139ebd9ae5547b3144dcf68d4cb1fd`). No online source or mapping lookup was used.

## Bounded trace and paired evidence

The requested operation is the local player path through `PlayerEntity.travel(Vec3d)`, with ability-flight active, not riding, dry, currently fall-flying with a usable default chest-slot Elytra. In both versions, `PlayerEntity.travel` snapshots position, handles swimming first, then checks `abilities.flying && !hasVehicle()`. That branch stores the pre-super vertical velocity and fly speed, calls `super.travel`, restores vertical velocity to the saved value times `0.6`, restores the prior fly speed, sets `fallDistance` to `0.0F`, and directly calls `setFlag(7, false)` after movement. The else branch calls `super.travel`; both then run the same distance-stat method. The complete method is byte-for-byte text-identical across the two canonical mapped files at lines 1362–1390.

The called `LivingEntity.travel(Vec3d)` gliding branch is also checked with its water/lava gates and `move` call. For the stated dry, active-Elytra, nonpassenger precondition it follows the same gliding math and `move` sequence on both sides. The flag clear inside that method remains gated by `onGround && !world.isClient`, so it does not account for the local-client clear. `LivingEntity.tickMovement` calls `initAi()` immediately before the travel dispatch. Both `initAi()` methods validate flag 7 against `!onGround`, `!hasVehicle()`, the equipped chest-slot item being Elytra, and `ElytraItem.isUsable`; its state writes remain server-only. The Elytra usability threshold is unchanged (`damage < maxDamage - 1`; the getter name differs). Entity flag 7 uses the same bit-mask read/write implementation. These checked caller/gate paths show no changed replacement or prerequisite for the direct local clear.

Exact manifest-matched source hashes:

| Source | 1.14.1 SHA-256 | 1.14.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398` |
| `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| `net/minecraft/entity/Entity.java` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` |
| `net/minecraft/item/ElytraItem.java` | `3eec99c8e60c1acdcccf64435c0be55a09d121dbd30a911d1ac7e10bdb7985df` | `63d7921eea162bf0194edd505ed195e7f0321c53c1298ce64daabf96b255e530` |

## Frozen provisional conclusion

**No 1.14.1→1.14.2 movement change is present in the specified post-real-move local ability-flight direct flag-7 clear.** Both exact versions clear flag 7 under the same `abilities.flying && !hasVehicle()` gate, after `super.travel` and the same velocity/fly-speed/fall-distance writes. Under the requested active Elytra/dry condition, the direct clear ends the local gliding flag after that movement in either version. The inspected item API rename and server-side Elytra durability application do not alter this client-side operation. This is a bounded no-difference verdict, not a claim about all player movement in the pair or every possible ability/equipment state.

## Post-freeze report comparison

After hashing the source-only freeze, I opened only the assigned `c-mid-1141-1142-ability-flight-glide-clear-source/report.md`; its SHA-256 matched expected `641d9498904e5271e3cb0008871a53e566c8595f11848e303d576258b1852ad5`. The claim matches the frozen source evidence and is independently accepted as a bounded no-difference.
## Independent audit of the assigned report

The only assigned author artifact was opened after the frozen source section was written and hashed (`57d25fc9650d80504f5baaeb25db811ccdc629017be3ba759909fe8aabd0b5bc`). Its SHA-256 exactly matches the assigned identity: `641d9498904e5271e3cb0008871a53e566c8595f11848e303d576258b1852ad5`.

I independently checked the report's additional client-toggle claim against `ClientPlayerEntity.tickMovement`. With `abilities.allowFlying`, the jump double-tap branch toggles `abilities.flying` and updates abilities in the same order on both sides; adjacent Elytra-start request gates require the player not already to be fall-flying and not already to be ability-flying, and check the default chest slot for usable Elytra. The 1.14.2 method is six lines shorter in its preceding sprint/input area, but this toggle/request block has the same conditions and order. It does not supply a release-specific behavior for the bounded witness. The report's claimed source hashes for both client files match the manifests and current file bytes: 1.14.1 `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933`; 1.14.2 `90c96701849c9212ea7714f02e7bc9011d0a7d0aaf1f7b46464497ba3ca14947`.

I also independently verified that no `decompiled_minecraft/.ready` root marker exists. The report's scope boundary is accurate: this review does not audit the full pair or claim parity across other player-tick branches. It is accurate on its core no-difference finding. Its reachable sequence is coherent: client ability flight is toggled before travel; `PlayerEntity.travel` enters the ability-flight branch; inherited dry active-Elytra travel performs its glide `move`; then the player wrapper restores its post-move vertical velocity/fly speed/fall distance and clears flag 7 on the logical client. The inherited ground cleanup remains server-only and does not replace that direct clear.

**Independent verdict: ACCEPT — source-confirmed bounded no-difference.** The submitted report's claim, release applicability and evidence identities agree with the frozen independent review. This accepts only the specified 1.14.1→1.14.2 Yarn creative-flight/Elytra-glide slice; it does not close pair coverage or imply runtime verification.
