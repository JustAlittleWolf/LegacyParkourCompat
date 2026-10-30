# Product review supplement: glide alias applicability closure

## Immutable binding

- Original independent product review: `independent-review.md`, SHA-256 `5CC948CAFE69C72009FD37A79137EFEC2B9B99A10F0D39A69CE58684012BC9F5`. That artifact is preserved unchanged.
- Product implementation commits reviewed there: `0e888d844f3fe84b2b2547f8b995b7c1c0b65b28` and import fix `435f3e14fe543a3e31764abdd1a205611a84e670`.
- Current tree at this supplement: `817034e51b53ec382a4e4ab06598581dfdb27a85`. Its later product commit touches the unrelated moving-fence edge scan; all glide product files and the resolver/runtime files listed in the original review retain their recorded hashes.
- Accepted glide operation finding: `ED7AC76568ADF100F48D92B27D42785AE703BFD0B7631E32D1850D4E50A1A44E`.
- Independent exact-source alias audit: `.native-source/20261030/ordinary-pool/glide-alias-extension-review/audit.md`, SHA-256 `2402D7193D836A81A7506E976D1E8E3B5CA62CA1B5880209F240E9135C7A522E`.
- Corrected alias source set, all reviewed by that audit:
  - `source-extension.md` — `55C4700AFEF47ED9426F587DE41E6D169CA8C9EC11EA71D46C93B3ECD841BEA0`
  - `source-extension-correction-1.md` — `BF50B8FF9727BF990F6C14A6DD9C8BDCC054C976320DDA52D723BCA8BD691E21`
  - `source-extension-correction-2.md` — `1AEA484B1AFE839143A07C9D525B1A159352F8B5EBD9A41924DF3D9A95E3C351`
  - `source-extension-correction-3.md` — `0B331BB0AF10F52C1BFCACFEC16ADC80D3030595C89F1BA60A5B3CE5475168BA`

## Updated verdict

**The previously open alias-coverage gate is closed. The product implementation is accepted for the exact stable profile range 1.9 through 1.12.2, subject to the same static-review limits in the original verdict.**

The independent audit checks the exact glide branch, source/provenance identity, local-movement and fluid gates, float lift narrowing, double `velocityY`, and acceleration expression for every stable release in that range: 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4; 1.10, 1.10.1, 1.10.2; 1.11, 1.11.1, 1.11.2; and 1.12, 1.12.1, 1.12.2. This closes the seven aliases listed as missing in the original product review. Corrections 1–3 are all included; in particular, the bound source order is acceleration, dive correction, pull-up correction, horizontal steering, then drag.

This supports the `V1_12` provider applying the old default-gravity expression to all 1.9–1.12.2 profiles. The existing `V1_8_9` native provider and resolver direction preserve native behavior for pre-1.9 selections, and no acceleration provider resolves for 1.13 and later. The source audit remains operation-only: it does not claim pair-wide movement coverage, first-introduction proof by itself, or runtime parity. The earlier exact 26.2 bytecode/ordinal/LVT review, client-player and logical-side gates, Slow Falling fallback, gravity/coefficient fallback, hook granularity, and native fallback findings remain unchanged.

## Product file identities rechecked

The following glide-relevant identities still match the original review at current HEAD:

| File | SHA-256 |
|---|---|
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/LivingEntityMixin.java` | `49769C95874EEDC9FEBD25681B1C0180D9908E13A243EC511E514593EA3186BF` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/GlideVerticalAcceleration.java` | `497B98A2DC6D4AC543FBD8666D6DF4E46857C883D53B53FBC7A7E94C94C78E92` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_8_9/NativeGlideVerticalAcceleration.java` | `30486F5091D29C34C9D46969300A7972EABAB85513EECDCBE02BD2BCE303091E` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/travel/GlideVerticalAccelerationHook.java` | `02B15CEB7AEB7A0D4F5DCF84DCE634B1A184D64351F4539BD194335C19926F0E` |
| `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` | `57F3B806F65B48AD0ABB2612CDC67BF84C94D70C3A4A8EA230FD7D6F962AB763` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java` | `606529E8FDC96B3EE34B70224AA82B954065B6DF6A8D405DF835AAE8B7703B73` |

No product files were changed for this supplement. No build, test, game, or runtime launch was performed.
