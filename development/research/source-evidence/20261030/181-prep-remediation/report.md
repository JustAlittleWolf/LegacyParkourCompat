# 1.8.1 Vineflower enum-switch preparation remediation

## Conclusion

The observed 59 ERRORs are consistent with loss of synthetic enum-switch helper ownership metadata during the 1.8.1 remap. The exact `BlockElement` comparison shows a concrete input difference: its switch-map class is an un-nested top-level class in 1.8.1 (`clv` -> `BlockElement__85922567`) with no `InnerClasses` or `EnclosingMethod` attributes, while the corresponding 1.8.2 class (`bgi$1` -> `BlockElement$85922567`) carries both attributes naming `BlockElement` as owner. The 1.8.2 mapping source also encodes `$` in the intermediary and named class names; 1.8.1 uses a flat `C_85922567` intermediary name and double underscore in the named name.

The source-preparation failure is reproducible in principle from the pinned inputs, but no decompiler run was performed. The prior report records the 59 messages and abort gate. This investigation only inspected cached jars, mappings, task source, and the Vineflower source jar.

## Evidence

- `MinecraftDecompileEngine.decompileMappedVersion` applies TinyRemapper to the client jar, then decompiles that remapped jar. `remap` enables `rebuildSourceFilenames(true)` but has no nesting-metadata reconstruction step.
- `MinecraftDecompileEngine.decompileJar` uses Vineflower 1.12.0 with one thread, fixed options, and `allowedPrefixes("net/minecraft", "com/mojang")`. It counts every ERROR and aborts before publication when any occur. The failed-method marker scan and required-source checks follow decompilation and also remain in force.
- In cached `client-ornithe-feather.jar`, 1.8.1 contains `net/minecraft/client/resource/model/BlockElement__85922567.class`. `javap -p -v` shows only `SourceFile` metadata; no `InnerClasses` or `EnclosingMethod`. The raw 1.8.1 `client.jar` `clv.class` likewise has only `SourceFile` and no ownership metadata.
- The same mapped jar in 1.8.2 has `BlockElement$85922567.class`; `javap -p -v` shows `InnerClasses` entry `BlockElement$85922567 of BlockElement` and `EnclosingMethod: BlockElement`. Its raw `bgi$1.class` already has both attributes.
- Cached Feather Tiny lines: 1.8.1 maps `clv` -> `net/minecraft/unmapped/C_85922567` -> `.../BlockElement__85922567`; 1.8.2 maps `bgi$1` -> `net/minecraft/unmapped/C_82588014$85922567` -> `.../BlockElement$85922567`.
- Vineflower 1.12.0 `SwitchHelper.simplify` reconstructs enum switches from the generated integer-array field's class initializer, and logs an ERROR if a `tableswitch` case constant cannot be found in the recovered map. This matches the earlier `const(1) not found, available: {}` messages. It confirms the strict diagnostic is a failed reconstruction, not a memory symptom.

## Minimal faithful remediation design

Add an optional, mapping-aware post-remap classfile metadata repair for **only proven synthetic enum-switch helper classes whose exact owner relation is in the mapping data**. For 1.8.1 Feather, normalize the proven flattened helper binary name from `Outer__<suffix>` to `Outer$<suffix>` and use ASM (already present in buildSrc dependencies) to add the matching `InnerClasses` entry and `EnclosingMethod` attribute pointing at `Outer`. Do not change method bodies, fields, constants, descriptors, or control flow. Keep the feature limited to remapped legacy inputs; leave all classes with existing metadata untouched. A narrowly scoped owner/suffix allowlist can prove the first remediation; a generalized rule should be added only after inventorying every candidate mapping and checking that the helper has the expected enum-switch-map bytecode and that the owner class exists.

The name and ownership information can be derived from the exact Feather mapping entry and corroborated against the helper's bytecode references to the owner enum(s). The 1.8.1 vs 1.8.2 bytecode comparison confirms the six `Direction.ordinal()` assignment cases and catches are present; the semantic data is not missing. Adding only classfile nesting metadata and the canonical nested binary name leaves that bytecode intact. Vineflower then sees the same ownership structure present in the 1.8.2 input. Retain all existing zero-ERROR, failed-method-marker, required-source, provenance, and publication checks.

A testable candidate patch can be built in isolation without changing historical game semantics: it is a preparation-only transformation that rewrites class metadata/name and preserves bytecode payload. It must still be validated by hashing the normalized method/field code before and after transformation, inspecting resulting `InnerClasses` / `EnclosingMethod` with `javap`, and eventually running an authorized exact-version decompile to verify zero ERRORs and complete source checks. No such patch or generation was performed here, so this is a source-supported design, not proof that Vineflower will pass every one of the 59 cases.

## Why simpler alternatives are unsupported

- A heap increase cannot restore absent class ownership information; the logged failure is an enum map lookup error.
- Renaming `__` to `$` alone is not enough to reproduce the 1.8.2 input because the 1.8.1 remapped helper also lacks both classfile attributes.
- Relaxing the ERROR gate, ignoring failed-method markers, or publishing failed output would undermine the source-validity checks.
- Changing enum-switch decompiler preferences is not implicated by the inspected Vineflower path.
- Selecting another mapping build would change pinned evidence and requires its own reviewed mapping/input comparison; no mapping build change is demonstrated as safe by this inspection.

## Scope and non-actions

Read-only inspection only: no canonical/shared source, cache, `buildSrc`, or product files changed; no downloads, remaps, source generation, tests, build, or game launch. The only created file is this report under `.native-source/20261030/181-prep-remediation/`.
