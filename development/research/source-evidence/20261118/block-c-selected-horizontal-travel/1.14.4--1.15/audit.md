# Independent source audit: 1.14.4 → 1.15 horizontal travel arithmetic

## Assignment boundary

Compared only exact decompiled Mojmap sources for stable Java 1.14.4 and 1.15. The owned slice starts with the movement vector already consumed by `LivingEntity.travel(Vec3)` and covers ordinary dry, non-fall-flying horizontal travel for a nonsprinting local player: input acceleration, ground/air speed selection, and horizontal post-move drag. Block-friction coefficient/location, yaw, and `onGround` are supplied dependency inputs. Keyboard/input production, movement-vector sampling, vertical gravity and thresholds, collision/support/stepping, sprint/timers, special movement modes, and direct environmental effects are outside this audit.

## Source identity and integrity

The inspected roots are `decompiled_minecraft/1.14.4/mojmap/` and `decompiled_minecraft/1.15/mojmap/` in the primary checkout. Each exact version's `mojmap.ready.json` identifies the expected version and mapping. The 1.14.4 marker SHA-256 is `EC7B6A6D9BA72F8A19908527977C970A8E2B3007B0E0B4323A725FB5943D3043`; its `mojmap.provenance.json` SHA-256 is `9D1AD405E1E787D2FCA5ED1897E6797B1F3CD79527DA50D713232F46E2F30D97` (the marker itself has no provenance field). The 1.15 marker SHA-256 is `C6B66B63BAFAF2096AD7606111F1D0FAE1F2178D624D6A8C9DED5AD3AC1AAA31`; its ready record binds `mojmap.provenance.json` SHA-256 `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`.

For both versions, the ready record's `versionId` is exact and `mapping` is `mojmap`. The declared source-manifest SHA-256 matches the actual `mojmap.sources.sha256` file. Every manifest entry was rehashed against the source tree: 1.14.4 has 3,250 files / 17,080,207 bytes and 1.15 has 3,329 files / 17,522,461 bytes, matching the ready records; each has zero missing or mismatched entries. This verifies the inspected source bytes against the published manifests and the requested ready-marker identities. 1.14.4 provenance records a run whose command requested exact `1.14.4` with `mojmap`; 1.15 provenance records exact `1.15` and the ready record binds it. No source generation or external lookup was used.

## Retraced evidence

All paths below are repository-relative and line numbers are one-based in the verified trees.

- `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 1782–1842: after the effective-AI/local-control and dry/non-fall-flying gates, it obtains friction `v`, chooses horizontal multiplier `w = onGround ? v * 0.91F : 0.91F`, calls `moveRelative(getFrictionInfluencedSpeed(v), vec3)`, and proceeds to movement.
- `decompiled_minecraft/1.15/mojmap/net/minecraft/world/entity/LivingEntity.java`, `travel(Vec3)`, lines 1831–1891: same gates and same operations, casts, constants, and ordering for `v`, `w`, acceleration, and movement.
- 1.14.4 `LivingEntity.java` lines 1957–1963 and 1.15 `LivingEntity.java` lines 2006–2012: `getFrictionInfluencedSpeed(f)` is identical: on ground, `getSpeed() * (0.21600002F / (f * f * f))`; in air, `flyingSpeed`. Both `getSpeed()` implementations return the stored `speed` field.
- 1.14.4 `Entity.java` lines 1080–1095 and 1.15 `Entity.java` lines 1063–1078: `moveRelative` adds the `getInputVector` result to current delta movement. The complete arithmetic body is identical: `lengthSqr`, zero below `1.0E-7`, normalize only above `1.0`, scale by the supplied float, compute yaw sine/cosine after degrees-to-radians conversion, then rotate horizontal components as `(x*cos - z*sin, y, z*cos + x*sin)`. `getInputVector` changes access from `protected static` to `private static`; the inherited `moveRelative` call path and its arithmetic are unchanged.
- 1.14.4 `Player.java` lines 1423–1426 and 1.15 `Player.java` lines 1501–1504: player `getSpeed()` is identical, returning `(float) getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()`.
- In both `LivingEntity.travel` bodies, the final ordinary dry branch writes horizontal velocity as `vec38.x * w` and `vec38.z * w`; these expressions are identical (1.14.4 line 1862; 1.15 line 1911). The vertical component is excluded.

## Result

No change found in the owned horizontal travel arithmetic: the acceleration transform, player speed selection, ground/air branch, constants, casts, operation order, and horizontal drag expressions match across these two releases. The `getInputVector` visibility change does not alter the inherited player travel calculation.

A nearby dependency-location change is visible but deliberately not accepted as an owned difference: 1.14.4 `LivingEntity.travel` line 1837 constructs `BlockPos(this.x, boundingBox.minY - 1.0, this.z)`; 1.15 line 1886 calls `getBlockPosBelowThatAffectsMyMovement()`, whose 1.15 definition is `Entity.java` lines 600–602 and uses `boundingBox.minY - 0.5000001`. Since friction coefficient/location is an assigned dependency input, this audit does not assess its behavior or conclude that resulting friction inputs match. Yaw and `onGround` are likewise assumed inputs, not audited producers.

## Limits

This is a bounded endpoint source comparison for 1.14.4 versus 1.15, not proof of the intermediate patch introduction boundary, trajectory-level parity, or full player-tick coverage. It makes no claim about friction block selection, collision/movement resolution, environmental branches, upstream vector production, sprint attribute setup, or runtime parity.

## Independent phase-2 review

**Verdict: QUALIFIED ACCEPT** for the report's bounded arithmetic conclusion, bound to the exact author report bytes: `author/report.md`, size 5,655 bytes, SHA-256 `32C8ECE9CDD57ED19E15EE52C724B56E74A8D3988DCD70914BEC48EF5CFF2FC3`.

I independently agree that the owned horizontal travel arithmetic has no difference when the supplied block-friction value/location, yaw, `onGround`, and consumed input vector are held as dependencies. I retraced `LivingEntity.travel`, `Entity.moveRelative`/`getInputVector`, `LivingEntity.getFrictionInfluencedSpeed`, and `Player.getSpeed`; gates, float constants, normalization/rotation math, expression order, and X/Z writes match. I also checked the report's ordinary `Player.travel` delegation and `flyingSpeed = 0.02F` default references. The report correctly leaves the 1.14.4 versus 1.15 friction-position lookup change outside this slice. Qualification: this verdict accepts only that conditional bounded result; it does not accept matching friction inputs, close other pair/lane coverage, or claim trajectory/runtime parity.

The author report's supplemental exact-source identity statements are consistent with the reviewed ready records and manifests; the stated 1.14.4 verification record hash was independently checked and its source verification summary matches the report. This verdict does not rely on its unrelated movement coverage or claim closure.