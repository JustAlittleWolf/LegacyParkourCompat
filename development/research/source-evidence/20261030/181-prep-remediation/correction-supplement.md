# Correction supplement: Vineflower inner-name validation

This supplement preserves the original report and corrects its proposed first remediation option in light of the independent audit.

## Correction

Adding `InnerClasses` / `EnclosingMethod` while preserving the 1.8.1 helper's flattened binary name is **not a viable first fix under the current Vineflower options**. Vineflower 1.12.0's `ClassesProcessor` checks member-class names when `VALIDATE_INNER_CLASSES_NAMES` is enabled (source lines 206–207); `IFernflowerPreferences` defaults that option to `1` (line 495). A member entry is rejected unless the helper binary identity equals `owner + '$' + simpleName`. `BlockElement__85922567` cannot satisfy that condition while retaining its identity. Its ownership metadata would therefore fail the configured structural check. Disabling the validation option globally would broaden the behavior change and is not the recommended minimal candidate.

Accordingly, the first viable candidate must change the mapped helper's binary identity to the canonical nested form and update all references consistently, then add the matching nesting attributes. This is **not** metadata-only. The narrowest preparation path is a mapping overlay applied before the existing TinyRemapper pass (so TinyRemapper rewrites the class entry and symbolic references together), followed by ASM metadata insertion on the exact remapped helper. Do not rename only the helper entry or rewrite only its `this_class`; that would leave owner bytecode and any other symbolic references inconsistent. Preserve helper method/field code and all unrelated archive entries. Verify the class identity/reference rewrite separately from the metadata additions.

## Exact initial allowlist entry

Only this one pair is established well enough for an initial isolated candidate; it is a proof target, not an allowlist for the other 58 diagnostics:

| Minecraft / mapping identity | Mapped helper | Owner | Canonical identity |
| --- | --- | --- | --- |
| `1.8.1`, Ornithe Feather `1.8.1+build.2`; pinned mapping jar SHA-256 `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `net/minecraft/client/resource/model/BlockElement__85922567` | `net/minecraft/client/resource/model/BlockElement` | `net/minecraft/client/resource/model/BlockElement$85922567` |

Exact Feather Tiny class row: `clv` → `net/minecraft/unmapped/C_85922567` → `net/minecraft/client/resource/model/BlockElement__85922567` (cached `feather-gen2-1.8.1+build.2.tiny`, line 34231). The mapping artifact pin is in `.minecraft-decompile-cache/yarn/feather-gen2-1.8.1+build.2-mergedv2.jar.pin.json`. The canonical `$` path is absent from the cached 1.8.1 remapped jar; the flat helper path is present.

Eligibility proof for this entry, from static `javap -p -v/-c` inspection of the cached remapped jar:

- Helper class flags: `ACC_SUPER | ACC_SYNTHETIC`; the only declared field is `f_65874731:[I`, with `ACC_STATIC | ACC_FINAL | ACC_SYNTHETIC`; no `InnerClasses` or `EnclosingMethod` attributes.
- `<clinit>` allocates from `Direction.values().length`, writes `Direction.ordinal()`-indexed values for DOWN, UP, NORTH, SOUTH, WEST, EAST as 1 through 6, and has a `NoSuchFieldError` handler around each assignment.
- `BlockElement#getTextureCoords` reads `BlockElement__85922567.f_65874731:[I` and is the first recorded failed enum-switch reconstruction.
- In the 1.8.2 comparator, `BlockElement$85922567` is synthetic and has `InnerClasses` entry naming `BlockElement$85922567` as a static member of `BlockElement`, plus `EnclosingMethod: BlockElement` with no method. This supports the owner and intended simple name, but does not prove the preparation fix clears Vineflower's error.

For a candidate, fail closed unless the build is exactly the specified version and mapping artifact; the exact flat helper, owner and expected canonical identity are present/absent as above; the owner exists; the helper has no conflicting nesting attributes or unexpected members; its enum-switch shape matches the inspected contract; and owner bytecode references the expected helper field. Any future allowlist entries require the same individual mapping, owner-reference, bytecode-shape, collision and comparator evidence. The 59 errors do not establish that all have this cause.

## Preservation and verification contract

The mapping overlay must be applied only to this exact class mapping, and TinyRemapper must perform the identity/reference rewrite across the input. Then add class-level `InnerClasses` and `EnclosingMethod` metadata consistent with the canonical identity and the 1.8.2 comparator. Use a before/after verifier that distinguishes the intentional class identity/reference edits from the two intended nesting metadata additions and proves that helper method instructions, exception handlers, fields, descriptors, and unrelated archive entries remain unchanged. Keep the existing zero-ERROR abort, failed-method marker scan, required movement-source checks, provenance and publication gates unchanged.

No mapping, jar, build source, canonical source, or prior report was modified. No decompile/build/test/game operation was run. This correction is a source-supported candidate contract; exact-version decompile validation remains with the designated preparation owner after explicit authorization.
