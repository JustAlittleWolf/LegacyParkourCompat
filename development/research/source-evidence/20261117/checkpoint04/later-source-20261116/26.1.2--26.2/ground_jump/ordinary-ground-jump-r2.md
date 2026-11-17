# Ordinary local-player ground jump — 26.1.2 → 26.2

**Scope verdict:** bounded ordinary, non-flying, dry grounded local-player jump path; no source difference found in the owned path. This is a partial slice only. It does not accept/freeze the pair, assign an intermediate onset, or establish runtime parity.

## Exact source identity

Both endpoints are exact-version decompiled unobfuscated sources. Identity values below were copied from the previously independently accepted pair-local air-travel report and checked against the current root sidecars. For the five listed Java files, the local SHA-256 matched both the root unobfuscated.sources.sha256 entry and the actual source bytes.

| Endpoint | Ready marker SHA-256 | Provenance sidecar SHA-256 | Source manifest SHA-256 | Artifact sidecar SHA-256 | Namespace |
|---|---|---|---|---|---|
| 26.1.2 | f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7 | 8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a | 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0 | 89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92 | unobfuscated |
| 26.2 | f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f | a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1 | a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 | ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba | unobfuscated |

Source file hashes (26.1.2 / 26.2):

- net/minecraft/client/player/LocalPlayer.java: 433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe / 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6
- net/minecraft/world/entity/player/Player.java: 44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d / 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531
- net/minecraft/world/entity/LivingEntity.java: c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd / 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a
- net/minecraft/world/entity/Entity.java: 8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf / 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5
- net/minecraft/world/entity/ai/attributes/Attributes.java: 8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5 / 4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c
- net/minecraft/world/effect/MobEffects.java: 532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684 / 532df5c7cd47edd3d9d091c2eae0962c279224c8f5f206860e9df45bc3b0c684

The artifact sidecar identities and underlying payload caveat are unchanged from the accepted air-travel report. This report makes no new claim about the availability or byte verification of the original client JARs.

## Ordinary local-player input and jump path

The input source is the local player's LocalPlayer.applyInput() override (LocalPlayer.java:692-704 at both endpoints). For the controlled-camera path, it writes movement impulses from modifyInput(input.getMoveVector()) and assigns jumping = input.keyPresses.jump(); otherwise it delegates to super.applyInput(). The exact body matches across endpoints.

LocalPlayer.aiStep() snapshots the prior jump state and processes input.tick() at :777-787. Auto-jump can call input.makeJump() when autoJumpTime > 0 (:788-793), before the base player/entity step. The previously accepted aist_ep_pre r4 report covers the surrounding LocalPlayer/Player tick dispatch and creative-flight trigger/countdown slice: report SHA-256 203C01B767A8CFC225066498C5FB2F916C928812DCADF8744DD8BCC1301263EB, independent ACCEPT verdict SHA-256 D9106D101F86B5E080643AF94F1ED05CF9CF361436F427B9C69F2CC436151F49. That packet's jump call is the separate mayfly flight-toggle case; it does not cover the ordinary input-field-to-ground-jump consumer traced here. The accepted air-travel packet also explicitly left jumping unexamined (report A331B73C457DC9FA37338EE06E8735EA6290D94331C13CF62B87D0DBAA084EA9, audit A922A47450A70A5A07DADCC801881109E729DE269DD85A796F69BE472F131149).

In LivingEntity.aiStep(), both endpoints call applyInput() before the jump section (26.1.2 LivingEntity.java:3017-3032; 26.2 :3084-3099). Player.isAffectedByFluids() returns !abilities.flying in both (Player.java:875-877). Thus a non-flying local player with jumping set reaches the shared jump selector. For an ordinary dry grounded state, isInLava() and isInWater() are false, inWaterAndHasFluidHeight is false, and onGround() is true. The selector then calls jumpFromGround() when noJumpDelay == 0; after the call it assigns noJumpDelay = 10 (26.1.2 LivingEntity.java:3032-3047; 26.2 :3099-3114). The dry grounded gates and ordering match. The enclosing selector's fluid-specific branch differs for lava in 26.2 (isInShallowFluid(FluidTags.LAVA)); that is not part of this ordinary dry-ground claim and is left to fluid-specific coverage.

## Jump impulse and inputs

LivingEntity.getJumpPower() delegates with multiplier 1.0F. The shared formula is identical: (float)getAttributeValue(Attributes.JUMP_STRENGTH) * multiplier * getBlockJumpFactor() + getJumpBoostPower() (26.1.2 LivingEntity.java:2339-2348; 26.2 :2376-2385). getJumpBoostPower() contributes 0.1F * (amplifier + 1.0F) only when MobEffects.JUMP_BOOST is present. The JUMP_STRENGTH attribute's declared base is 0.42F on both (Attributes.java:48-50 and :58-60); LivingEntity.createLivingAttributes() registers it on both (:318-333 and :332-345), and Player.createAttributes() extends that builder (Player.java:205-212 and :206-213). No Player override of these jump methods was found in either exact Player.java.

Entity.getBlockJumpFactor() is identical (Entity.java:1015-1019 / :1079-1083): read the current block's factor, read the movement-support block's factor, and use the latter only when the current factor equals 1.0. LivingEntity.jumpFromGround() is likewise identical (LivingEntity.java:2352-2363 / :2389-2400): if jump power is greater than 1.0E-5F, preserve horizontal movement and set Y to Math.max(jumpPower, movement.y); while sprinting, add the same yaw-derived horizontal impulse using float angle conversion and 0.2; then set needsSync = true.

No difference was found in the local jump-input assignment, auto-jump key injection in the reviewed lines, ordinary dry-ground eligibility gate, jump-power formula, jump-boost contribution, block-factor lookup, or velocity write/order. This is a source comparison of the shared vanilla path, not a claim that a particular attribute modifier, effect instance, or block factor is active in any particular world.

## Boundaries and next action

- Covered here: ordinary non-flying, dry, grounded local-player key/auto-jump input through the LivingEntity.jumpFromGround() velocity write; shared attribute/effect/block-factor inputs as source formulas.
- Reused, not re-audited: accepted aist_ep_pre r4 tick/creative-flight prelude and accepted ordinary-air travel report, with identities above.
- Excluded/separate: mayfly creative-flight toggle (covered by aist_ep_pre), fluid and lava jump branches, fall-flying activation, vehicle jumps, server-authoritative packet handling, and non-player jump behavior.
- Still open: block-specific jump-factor providers and movement-support block-state dependencies; effective runtime attribute modifiers/effects; remaining tick/travel/collision/provider inventories and whole-pair coverage. No packaged resource or live-world claim was necessary for this shared-code comparison.

Next bounded audit: independently trace only the cited exact dry-ground call path and equalities, verify source identities/file hashes and the described reuse boundaries. Keep the 26.2 lava-specific selector change outside acceptance for this ordinary dry-ground slice; do not infer full-pair freeze.

## Immutable r2 binding addendum

This r2 preserves the complete r1 report as its unchanged prefix. It adds only the two exact input-dependency bindings requested by the first audit. It does not reopen or alter the already checked movement claims, result, scope, or limitations.

### Auto-jump input construction and record value

At both endpoints, LocalPlayer.aiStep calls ClientInput.makeJump() when autoJumpTime is positive (LocalPlayer.java:788-793). ClientInput.makeJump() (net/minecraft/client/player/ClientInput.java:21-31) replaces keyPresses with a new Input that preserves forward, backward, left, right, shift and sprint and sets the fifth constructor argument to true. The exact helper body and its source file are byte-identical at 26.1.2 and 26.2; SHA-256: 597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78.

The value type (net/minecraft/world/entity/player/Input.java:6) declares record components in the order forward, backward, left, right, jump, shift, sprint, so that fifth true argument is jump. LocalPlayer.applyInput reads the jump component as input.keyPresses.jump() (LocalPlayer.java:692-704, especially line 697). Input.EMPTY initializes all seven components false (Input.java:39); the serialized flag mapping uses bit 16 for jump and decodes that bit into the jump component (Input.java:21, 27-36). The exact Input.java file is byte-identical at both endpoints; SHA-256: ee9ce4cfd647e09c55a9562f917fa504c0fba081bcfbbeb15871cf70818bc447.

For both added files, those exact hashes were checked against the source bytes and the root unobfuscated.sources.sha256 manifest entries at both endpoints. The endpoint roots, ready/provenance identities, namespace and existing source-file hashes remain the same as the r1 identity section. No new assertion is made about keyboard device polling, autoJumpTime eligibility upstream, dynamic world state, or runtime behavior.

The resulting ordinary dry-ground input path is now source-bound through the auto-jump helper and jump record component, followed by the already reported LocalPlayer.applyInput assignment and LivingEntity.aiStep consumer. The previous bounded no-difference result is unchanged. This r2 is ready for a fresh narrow independent audit of the added ClientInput.java and Input.java bindings and their connection to the cited LocalPlayer uses; do not repeat the unchanged movement-method comparison or infer pair freeze.
