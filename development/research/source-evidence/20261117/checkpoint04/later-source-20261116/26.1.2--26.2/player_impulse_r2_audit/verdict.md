# Independent source audit — Player impulse r2

Verdict: ACCEPT for the report's bounded source claim. This is not full pair coverage, source acceptance beyond the audited slice, implementation evidence, or runtime-parity evidence. The pair remains partial and unresolved dependencies remain as stated in the report.

## Immutable input

Audited `build/later-source-20261116/26.1.2--26.2/player_impulse_response_revision_r2/report-r2.md`, SHA-256 `60f2d90004af374228c18a9cb629ee0b09bcbbb9eaa73a3f63ac23d269e4a444`.

## Checks

- Confirmed the reported source identities for 26.1.2 and 26.2 use the `unobfuscated` namespace and that the ready-declared source/artifact manifest digests match the local manifest bytes. 26.1.2's ready marker includes `clientSha256` and `artifactCount`; 26.2's does not. The report accurately attributes 26.2's client digest to `artifacts.sha256` and does not claim the absent client JAR was rehashed.
- Rehashed the cited `Player.java`, `LivingEntity.java`, and `LocalPlayer.java` files in both releases against each source manifest; all hashes match the report. Also checked the 26.2 `AbstractClientPlayer.java` hash against its manifest (SHA-256 `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc`). `LocalPlayer extends AbstractClientPlayer`, and `AbstractClientPlayer extends Player`.
- Confirmed 26.1.2 `Player.stabAttack` calls `causeExtraKnockback(target, 0.4F + getKnockback(...), oldMovement)` once when `dealsKnockback` is true. In 26.2, after the same `dealsKnockback && deflectProjectile(target)` early-return gate, it calls once with `0.4F` and then with `getKnockback(...)`. The first amount is positive; the second call may be non-positive, so the report correctly limits the second damping to cases where that amount is positive.
- Confirmed the corresponding Player `causeExtraKnockback` methods in both releases guard the response with `knockbackAmount > 0.0F`; after the target living/non-living response, each positive call multiplies the attacker's X/Z delta movement by `0.6` and clears sprinting. Thus two positive calls in 26.2 apply two sequential attacker damping writes, whereas the single 26.1.2 call can apply that write once. No ServerLevel gate appears in the Player `stabAttack` or `causeExtraKnockback` bodies.
- The report leaves the item/client invocation path unresolved. The inheritance facts alone do not establish that ordinary LocalPlayer gameplay reaches this method; the report makes no such claim. It also leaves `getKnockback` inputs/modifiers and other listed dependencies open.

No material discrepancy found in the r2 correction. This acceptance is limited to the source statements above and does not close attack reachability, modifier/resource dependencies, other movement areas, or pair coverage.