# Independent review: fall-flying sprint-gate applicability addendum

**Verdict: ACCEPT, conditional on the stated identical fall-flying state clear and no intervening synchronized shared-flags write that replaces the divergent sprint bit.** This is an applicability route into the existing 1.19.3→1.19.4 airborne sprint-speed operation, not a separate movement finding.

## Exact-source checks

All six source hashes in `airborne-sprint-fallflying-gate-source-hashes.tsv` match the local Mojmap files.

- 1.19.3 `LocalPlayer.aiStep` lines 709–717 starts sprint on the held key without an `isFallFlying` gate. Its following 719–728 block retains sprint if forward impulse and food are sufficient, there is no relevant collision, and the non-swimming cancellation condition is false; the sprint-key state is not part of this retention predicate.
- 1.19.4 `canStartSprinting` lines 1022–1030 includes `!this.isFallFlying()`, and `aiStep` lines 696–698 requires it for key-triggered sprint start.
- 1.19.3 `Player.aiStep` lines 513–517 writes `flyingSpeed` after `super.aiStep`; 1.19.4 `Player.getFlyingSpeed` lines 2112–2117 reads current sprint state, returning `0.025999999F` or `0.02F` outside ability-flight/passenger cases.
- 1.19.3 `LivingEntity.getFrictionInfluencedSpeed` lines 2246–2247 returns the stored field when airborne. 1.19.4 lines 2197–2198 calls the dynamic getter. Elytra travel uses its own branch (1.19.3 lines 2117 onward; 1.19.4 lines 2048 onward) and does not consult the sprint bit in its movement calculation.

## Reachability assessment

With the conditional sequence in the draft, the old client can enter sprint while fall-flying, then keep sprint after the sprint key is released because forward impulse remains sufficient. The 1.19.4 client cannot enter sprint in the same fall-flying state. This does not change the Elytra movement branch itself. If the shared fall-flying flag is then cleared before the next local `aiStep`, and no synchronized entity-data write overwrites the divergent sprint bit, old LocalPlayer remains sprinting while new LocalPlayer remains non-sprinting. Old `flyingSpeed` holds the sprint value written after the preceding tick; the new dynamic getter reads the current non-sprinting state during normal airborne travel. The movement-speed input to `moveRelative` therefore differs and changes the requested movement response.

The draft correctly conditions on the same external fall-flying state transition and does not infer packet production. It should state explicitly that no intervening synchronized shared-flags write replaces the local sprint-bit difference before normal air travel; otherwise the local state divergence is not guaranteed.

No builds, tests, game launches, or library execution were used.
