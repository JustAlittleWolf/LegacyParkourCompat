# Independent audit: 1.8.1 enum-helper allowlist

## Decision

**The 41-row allowlist is accepted as a complete static eligibility inventory for a proof candidate, with one required correction to the metadata contract.** The proposed canonical rename and consistent reference/path update are supported by the cache. However, the 1.8.2 comparator's `InnerClasses` entries have `inner_name_index = 0` (null simple name), not the numeric suffix. A candidate must reproduce that exact comparator shape: keep the canonical `$<suffix>` binary identity, set the inner name to null, use `ACC_STATIC | ACC_SYNTHETIC` (`0x1008`) on the `InnerClasses` entry, add the entry to helper and owner, and add helper-only `EnclosingMethod(owner, null method)`. Do not write `inner_name=<numeric suffix>`.

This is static source-preparation evidence only. No transformation or decompile was run; zero-ERROR Vineflower output remains unproved and requires the designated owner's authorized candidate generation through existing strict gates.

## Verified input identity

Inputs were read from `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache` and the daemon log from `C:\Users\Wolfi\.gradle\daemon\9.7.1\daemon-5508.out.log`. SHA-256 values were recomputed locally and match the evidence report's pins wherever it gives a value:

| Input | Verified SHA-256 |
|---|---|
| 1.8.1 raw `client.jar` | `66A749AD95ADCB929439155C0341309E89E437444867E108DCB36FD16493B0BD` |
| 1.8.1 mapped `client-ornithe-feather.jar` | `D2977E05623D692E2D73DACBD0D6328B9226F47D80F824711A6D5FE81DF09001` |
| 1.8.1 Feather Tiny mapping | `F26E3FEA2C580CF9EB2D5C47CD32A071F72177BA5E2C208C39196E669C8A383D` |
| 1.8.1 pinned Feather mapping artifact | `DAC40CBD25C4D76D99D4263FD9754AB0195EFD658756B6828FD06F0C4A8002F7` |
| 1.8.2 raw `client.jar` | `5CC5B24312F3D182BF06E7F1A4DA3A05CE94BE6C861D5C9995C86E6D8811FBE9` |
| 1.8.2 mapped comparator `client-ornithe-feather.jar` | `194B478E4FEDEBF3A95BA4557296A70A0548AF4974CB52DC29F94FAE5545983D` |
| 1.8.2 Feather Tiny mapping | `D5E27EBA2AA4D62251B3A573D072272802CDB2F1359CF96FD499C969E028AD9F` |
| 1.8.2 pinned Feather mapping artifact | `8235D85A8471D541F2BAD73C2A60C6132A9432FE804B98369982FC8DE847F37B` |
| retained daemon log | `C4C72FA36D6E88753D04C161CB14122356A98F27B64FD4E6CF0B9CA46D153491` |

## Allowlist completeness and per-entry checks

The table has 41 distinct helper identities, helper raw names, mapping rows, canonical names, and comparator rows. I parsed both Tiny files and confirmed for every entry that the listed 1.8.1 physical row, obfuscated class, intermediary class, and flattened named class match; the owner row and obfuscated owner match; and the 1.8.2 physical row maps to the listed canonical `$` name. The 1.8.1 mapped jar contains every flat helper and owner, contains no listed canonical helper collision, and each raw jar contains the listed raw helper.

I parsed class files directly from all three client jars. For every one of the 41 helpers, both raw and mapped 1.8.1 classes are synthetic, declare synthetic `static final [I` field(s), and their `<clinit>` invokes `ordinal()`, writes the switch-map arrays, and catches `NoSuchFieldError`; neither class has `InnerClasses` or `EnclosingMethod`. The 1.8.1 owner has no conflicting target nesting record. For all 41 logged 1.8.2 canonical helpers, `this_class` and archive path are canonical, helper and owner contain the matching static synthetic `InnerClasses` record with null `inner_name_index`, and the helper has `EnclosingMethod` naming the owner with method index zero. No mismatch was found in these checks.

I matched all 59 enum-switch diagnostics in the retained daemon log to the table. For every failing class/method, bytecode has `GETSTATIC` references to the assigned helper's `[I` field(s), and the set of referenced switch-helper owners is exactly that one helper. The line references are unique, cover 30514 through 30572 exactly once, and include one `const(2)` at 30540; the remaining 58 are `const(1)`. This validates the diagnostic-to-allowlist binding against the log and mapped bytecode.

## Corrected proof-candidate contract

Gate on exact Minecraft `1.8.1`, mapping label `ornithe-feather`, and the verified pinned Feather artifact identity. Apply an in-memory mapping overlay only for the verified intermediary-to-canonical pairs. TinyRemapper must rewrite each selected helper's class identity, archive path, and all symbolic references consistently. Then reproduce the 1.8.2 comparator's class metadata: add `InnerClasses(inner=canonical, outer=owner, inner_name=null, access=ACC_STATIC|ACC_SYNTHETIC)` to helper and owner, and add helper-only `EnclosingMethod(owner, null method)`. Preserve the helper class synthetic flag and all code and unrelated class/archive content.

Before remapping, fail closed unless the exact pinned source identity and each row's raw/intermediary/named identity match, the listed helper and owner exist, the canonical destination is absent, diagnostic method references and synthetic enum-switch shape match, and no conflicting nesting metadata exists. After remapping, structurally verify exactly the intended identity/path changes and references, null-name comparator-matched nesting records, no lingering flat helper identity/references, no other archive-entry changes, and preserved methods, instructions, fields, handlers, and unrelated attributes. Keep strict ERROR counting/abort, failed-method scan, required-source checks, provenance comparison, and publication behavior untouched. Candidate generation and strict zero-ERROR verification remain the preparation owner's work.

The earlier accepted `BlockElement` correction still applies: metadata-only annotation of the flattened class identity cannot pass Vineflower's member-name validation, so canonical identity remapping is needed. This audit's comparator correction changes the nesting entry's simple-name value to null; it does not weaken the requirement for a canonical binary name.

## Boundary

Read-only inspection only. No source tree, cache artifact, mapping, product file, or build file was modified. No download, transformation, decompile, build, test, or game launch was run.
