# Independent source audit: 1.11 → 1.11.1 near-vertical Elytra look path

## Verdict

**Accepted bounded no-difference claim.** For finite negative near-vertical pitch with finite, positive horizontal look magnitude, an ordinary dry locally controlled player already fall-flying in an Elytra reaches the same look-vector calculation, horizontal magnitude, pitch branch, and first `move` call in both exact releases. No difference is found in the assigned pre-first-move operation. This does not establish full pair coverage, a runtime result, or an intermediate patch boundary.

## Immutable input

- Frozen report: `c-glide-111-1111-near-vertical-look-alias/report.md`
- Input SHA-256: `022baace6a8cd0220798ab694f904acab49e521bec1534344c0d64d798a17a63`
- The report's frozen identity matched when read for this audit.

## Local source identity and integrity

Read-only source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.
Both exact release directories identify `ornithe-feather`; the ready markers and provenance identify their exact version IDs and Feather mapping builds. Actual local ready-marker-declared manifest and provenance hashes match:

| Release | Client SHA-256 | Feather merged JAR / Tiny SHA-256 | Source manifest SHA-256 | Provenance SHA-256 |
|---|---|---|---|---|
| 1.11 | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` / `46bd6702a0983ad667109aadad90b2e6390ed5cd5999561a8a904956f4baf9c0` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` |
| 1.11.1 | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` / `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` |

The three local source files match their respective source-manifest entries:

| Source file relative to release root | 1.11 SHA-256 | 1.11.1 SHA-256 |
|---|---|---|
| `ornithe-feather/net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `ornithe-feather/net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `ornithe-feather/net/minecraft/util/math/MathHelper.java` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |

The ready markers' manifest and provenance digests were recomputed from the on-disk files and matched exactly. Each selected Java hash above also matched both its manifest row and its on-disk bytes. Provenance records the exact resolved client versions and Feather artifacts; no source regeneration or network lookup was performed.

## Compared source behavior

- `LivingEntity.moveRelative`: 1.11 lines 1386–1425; 1.11.1 lines 1388–1427. Each body first gates on local/logical-side movement, then water and lava status (the ordinary case is dry), then `isFallFlying()`. It obtains `getLookVector()`, converts current pitch to radians, computes `j = sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)`, then horizontal velocity magnitude and full look-vector length in the same order. The cosine/gravity calculation, descending-and-`j > 0` dive branch, `i < 0.0F` pitch branch, horizontal alignment, drag, and first `move(MoverType.SELF, ...)` are ordered identically. For this bounded case `j` is finite and positive; the separate zero-horizontal case is outside the claim.
- `LivingEntity.getLookVector` and `getRotationVector(float)`: 1.11 lines 1914–1926; 1.11.1 lines 1916–1928. Both alias to `getRotationVector(1.0F)`; that branch supplies current pitch and head yaw rather than interpolated values.
- `Entity.getRotationVector(float,float)`: 1.11 lines 1170–1175; 1.11.1 lines 1229–1234. Both compute cosine of the negated yaw expression, sine of that same expression, negative cosine of negated pitch, sine of negated pitch, and construct `(g*h, i, f*h)` in the same order.
- `MathHelper.sin/cos`: source files are byte-identical. Both use `SINE_TABLE[(int)(x * 10430.378F) & 65535]` and the cosine offset `+ 16384.0F`; the static initializer is also the same. Thus the mapped trigonometric lookup path is unchanged.
- The ordinary chest Elytra condition is consistent on both sides: `flyingTick` preserves flag 7 only while airborne and not riding with an Elytra chest item that `ElytraItem.canFly` accepts (1.11 lines 1808–1827; 1.11.1 lines 1810–1829). In the already-active state specified here, `moveRelative` observes that flag through the identical `isFallFlying()` predicate.

## Scope and output identity

This audit covers only the exact local source identity and the ordinary dry, local, already-active Elytra look-vector path through its first move, with finite negative near-vertical pitch and positive finite horizontal look magnitude. It does not cover exact vertical/zero horizontal magnitude, malformed values, other Elytra callers, activation transitions as an independent operation, post-move effects, or the remaining player tick. No product files, wiki records, Git history, tests, builds, decompilation, runtime, or network were consulted or used.

Output: `c-glide-111-1111-near-vertical-look-alias-audit/verdict.md`.
