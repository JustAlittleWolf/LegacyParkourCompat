# Independent source audit: ordinary Slime bounce reachability

## Verdict

**ACCEPT** — audited report SHA-256: `DA498DEAEE4D95A632DA8847A4972A63FCE712655181A54A5E7319D4BB0FC79A`.

The report provides a source-supported ordinary local-player witness for the below-effective-gravity collision condition in 26.2, against the accepted 26.1.2/26.2 SlimeBlock source comparison. I independently checked the report bytes, endpoint identity sidecars, the cited Java-file digest rows, and the relevant 26.2 movement and collision operations. All eight cited Java paths at both endpoints match the corresponding `unobfuscated.sources.sha256` entries. The endpoint ready records bind the stated `unobfuscated` namespace, source manifests, and artifact manifests.

## Audit findings

The described carpet/full-block/slime arrangement is made from built-in registered states. `CarpetBlock` has a full-footprint 1/16-high shape and requires a non-empty block below; normal collision remains enabled. The selected arrangement therefore places the carpet surface 1/16 above the neighboring SlimeBlock surface. It is a valid ordinary map setup, without a claim that world generation naturally makes it.

The departure witness is a valid selected movement state: the local player is at the carpet edge with feet on its surface, has no vertical movement for the horizontal departure step, and uses ordinary horizontal input to clear the remaining carpet support while moving over the slime. In 26.2, local-instance authority updates `verticalCollision` and `onGround` from that move even with zero Y displacement. With no downward collision, support is cleared. The following normal air-travel update applies gravity and then vertical drag, yielding approximately `-0.0784`; the 0.98F multiplier makes its magnitude strictly less than 0.08. No Slow Falling, Levitation, flight, passenger state, fluid state, or attribute override is needed for this chosen witness.

On the following movement, the feet begin 1/16 above the slime top and the negative Y request exceeds that gap, so the vertical movement clips on the slime. After the move, `getOnPosLegacy()` resolves to the block below the player's center in this stated geometry, namely the SlimeBlock. The 26.2 below-collision restitution condition compares the current downward velocity magnitude against effective gravity and selects zero restitution for this value. The old 26.1.2 Slime callback behavior and the endpoint call-path comparison are already immutably bound by the accepted r2 report and audit cited by the audited report; this supplement makes no change to those findings. The pinned suppression-tag fact is not needed for this witness.

The chosen carpet-edge player position is an explicit initial condition for this source-level existence witness. The claim is that an ordinary legal map setup and normal player input admit the path; it does not establish a generated-world trajectory, runtime parity, frequency, survival inventory route, or behavior under arbitrary datapack/resource or synchronized attribute overrides. Those exclusions are correctly bounded and do not block this source witness.

## Identity and scope

Endpoint source identity is `26.1.2/unobfuscated` and `26.2/unobfuscated`, ready status, with ready/provenance/source/artifact digests exactly as recorded in the audited report. I reused the accepted r2 Slime call-path/callback comparison and the already pinned `SUPPRESSES_BOUNCE` resource finding without reopening their unchanged claims. This verdict covers only the supplemental ordinary reachability condition; it does not freeze the version pair or assert runtime parity.
