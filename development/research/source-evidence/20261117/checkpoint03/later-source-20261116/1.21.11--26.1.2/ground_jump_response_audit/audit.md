# Independent source audit: ordinary ground-jump response

Verdict: **ACCEPT** for the bounded NO_DIFF claim in `coverage.md`, SHA-256 `5E4B7AAF6C2EA93CBC7E3085E9039C8676D83CA4AD3724CBE27174B9F158812B`.

## Source identities

- 1.21.11 Mojmap ready marker identifies version `1.21.11`, mapping `mojmap`, client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`; ready-file SHA-256 `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`; provenance SHA-256 `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`; `mojmap.sources.sha256` SHA-256 `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`; artifact manifest SHA-256 `C8210B15012DCC4E109C2F73015B4117ABEC1F8AB04FDD375DBC11419E9E246C`.
- 26.1.2 unobfuscated ready marker identifies version `26.1.2`, mapping `unobfuscated`, client SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; ready-file SHA-256 `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; provenance SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`; `unobfuscated.sources.sha256` SHA-256 `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`; artifact manifest SHA-256 `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92`.

Both ready markers' source- and artifact-manifest digests match the local manifest bytes. I independently verified all six cited Java hashes at both endpoints against the files and their source manifests; all 12 match.

## Audit findings

The LocalPlayer input consumer and ordinary dry-ground route are correctly bounded. `LocalPlayer.applyInput` writes `jumping` from `input.keyPresses.jump()` for the controlled camera at 1.21.11 lines 652–665 and 26.1.2 lines 691–705. `LocalPlayer.aiStep` ticks input and then delegates through `Player.aiStep` to `LivingEntity.aiStep` at both endpoints. The mayfly flight-toggle and passenger paths are separate from the stated non-flying, non-passenger precondition.

For the stated dry grounded case, the inherited `LivingEntity.aiStep` path reaches `jumpFromGround()` only when on ground and `noJumpDelay == 0`, then sets the same 10-tick delay. The `Player.isAffectedByFluids()` override returns `!abilities.flying` at both endpoints, so it is true for the bounded non-flying player and introduces no endpoint difference. The key-state consumer, immobility gate, fluid branch guards, jump-delay operation order, and direct call are semantically equivalent.

The response also matches: `getJumpPower()` uses `1.0F`; the overload computes `(float)getAttributeValue(JUMP_STRENGTH) * multiplier * getBlockJumpFactor() + getJumpBoostPower()` in the same order; Jump Boost adds `0.1F * (amplifier + 1.0F)`; `jumpFromGround()` applies the same `<= 1.0E-5F` gate, writes Y with `Math.max(jumpPower, priorY)`, preserves X/Z, applies the same yaw-derived `0.2` sprint impulse when sprinting, and marks `needsSync`. The JUMP_STRENGTH default is `0.42F` at both endpoints and is included by both living/player attribute builders. The generic block-jump-factor lookup and `Block.getJumpFactor()` getter likewise match. The LocalPlayer sprint-start/sprint-possible gates cited by the report also match; the jump impulse reads the current sprint state.

The claim is supported as an endpoint-bounded NO_DIFF for ordinary dry-ground local-player jumps. This audit does not close fluid or vehicle jumping, mayfly/fall-flight toggles, auto-jump probing, block-specific jump-factor values, equipment/attribute producers, any intermediate release boundary, full-pair source coverage, implementation readiness, or runtime parity.
