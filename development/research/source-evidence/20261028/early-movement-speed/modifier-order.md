# Raw-bytecode supplement: 1.8.0–1.8.1 modifier collection order

## Scope and verdict

This supplement closes the source gap for the 1.8.0 and 1.8.1 movement-speed modifier collection algorithm. Exact pinned Mojang class files and their exact Feather Gen2 build.2 Tiny mappings were inspected with read-only `javap -p -c -s`. No class was executed, and no decompiled output, source cache, mapping artifact, or product file was generated or changed.

**Bounded verdict:** in both raw releases, each operation bucket is initialized as a `HashSet`; `getModifiers(operation)` returns that bucket; `addModifier` inserts the modifier into the bucket indexed by its integer operation; and calculation constructs a fresh `HashSet` from the local bucket, adds each ancestor attribute's bucket from nearest parent toward the root, then iterates the resulting set. The three calculation loops are always operation 0, then 1, then 2. `AttributeModifier.equals` and `hashCode` use only the UUID identity (`UUID.equals` / `UUID.hashCode`). These bytecode bodies and container choices match the bound 1.8.4 mapped source. Therefore the earlier 1.8.0/.1 gap about which collection determines within-category order is closed at the Minecraft bytecode level.

This does not bind a specific Java runtime's `HashSet` table layout or a mapping from arbitrary modern identifier-based modifiers to historical UUIDs. It does establish that the historical key material and bucket construction needed by a compatible adapter are UUID-based and category-specific; current public global `getModifiers()` enumeration cannot stand in for those historical sets. No claim is made for newly introduced modifiers with no historical UUID counterpart.

## Input identity and integrity

The pins below match the earlier raw-bytecode report `.native-source/20261028/1818-speed-boundary-raw-bytecode/REPORT.md` (SHA-256 `1edb89f388e395d96ed75df2c86066b57f7fdd1c07fb5518e260539d2a642032`). Before disassembly, I recomputed SHA-256 for each local version JSON, client JAR, mergedv2 mapping JAR, and Tiny mapping file; all matched that report. I also verified the client JAR SHA-1 against `downloads.client.sha1` in each hashed version JSON.

| Release | Version JSON package / SHA-256 | Client SHA-1 / SHA-256 | Feather Gen2 mergedv2 / SHA-256 | embedded Tiny SHA-256 |
|---|---|---|---|---|
| 1.8 | `1.8` / `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593` | `d722504db9de2b47f46cc592b8528446272ae648` / `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` | `feather-gen2-1.8+build.2-mergedv2.jar` / `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` | `0db2fa38dc74a0981164e53fc73152291cea5153e705b5d06d001ede01b682e8` |
| 1.8.1 | `1.8.1` / `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149` | `6edd2a3e3d76a7602c52f319f87cf11f7720b43b` / `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` | `feather-gen2-1.8.1+build.2-mergedv2.jar` / `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` | `f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d` |

The embedded Tiny files declare `official`, `intermediary`, `named` namespaces. Their class rows bind raw `ye` (1.8) and `yh` (1.8.1) to intermediary `C_13795309`, named `ModifiableEntityAttributeInstance`; `xz` / `yc` to `EntityAttributeInstance`; `yc` / `yf` to `AbstractEntityAttributeContainer`; and `ya` / `yd` to `AttributeModifier`. The operation helper `b(I)Collection` maps to `getAppliedModifiers(int)` in both Tiny files. Raw class symbols differ by release, while mapped identities are stable.

## Bytecode observations

For both versions, `javap -classpath <pinned-client.jar> -p -c -s <raw-class>` showed:

- The `ModifiableEntityAttributeInstance` constructor creates the operation, name, and UUID maps with `Maps.newHashMap()`. It loops integer values 0 through 2 and stores `Sets.newHashSet()` in the operation map for each value.
- The `getModifiers(int)` implementation returns the operation-map entry. Aggregate `getModifiers()` creates a separate `Sets.newHashSet()`, loops over buckets 0 through 2, and adds each bucket to that union. This aggregate collection is not the collection used by the calculation loops.
- `addModifier` checks duplicate UUID through the UUID-indexed map, then inserts the modifier into `modifiersByOperation.get(modifier.getOperation())` and the name set. Operation is the bucket selector; the modifier is the set element.
- `computeValue()` iterates `getAppliedModifiers(0)`, then `(1)`, then `(2)`. The helper obtains `getModifiers(operation)`, invokes `Sets.newHashSet(Iterable)`, and follows the attribute parent chain. For each parent with an instance, it adds that parent's same-operation collection to the result set before moving to the next parent. The returned set is the iterator consumed by the calculation loops.
- The `EntityAttributeInstance` interface exposes both `getModifiers(int)` and aggregate `getModifiers()` as distinct methods. `AbstractEntityAttributeContainer` stores instances by attribute in a map, keeps parent relationships in a `HashMultimap`, and resolves an instance by attribute for the helper's parent walk.
- `AttributeModifier` has a UUID field. Its `equals(Object)` compares UUID identity after the class check; `hashCode()` calls the UUID's hash code, returning zero only for a null UUID. The amount, operation, name, and serialization flag do not participate in equality or hash code. The explicit-UUID constructor stores the supplied UUID. The convenience constructor obtains a random UUID, so those IDs are not reproducible from modifier fields alone.

There is no hard-coded whitelist of legacy UUIDs in these classes: any UUID supplied to the explicit constructor is the supported identity for that modifier, while the convenience constructor generates a fresh random identity. The pinned source/mapping evidence identifies the identity mechanism, not a list or cross-version translation table of UUID values.

The 1.8 and 1.8.1 constructor, accessor, insertion, helper, and modifier identity bytecode has the same structure. Their calculate loops preserve identical category sequencing and arithmetic opcodes. The only differences relevant to symbol resolution are the expected obfuscated class/member constant-pool names. This supplements, rather than supersedes, the earlier raw-bytecode report, which covered only the arithmetic category sequence.

## Comparison with 1.8.4 mapped source

The canonical 1.8.4 Feather source tree is provenance-bound by `.native-source/20261028/1818-speed-boundary/REPORT.md` and its accepted audit. The corresponding exact sources have these SHA-256 values recorded in the provenance source map and independently checked there:

- `ModifiableEntityAttributeInstance.java`: `3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d`
- `EntityAttributeInstance.java`: `358d9b031fe281ab7837d3ae70d46654b0704db1e8c199234ba0bfd68a19692b`
- `AttributeModifier.java`: `ba990c7b8a57787a903a6083f373fe1180f2f9ee52e27c1a9984fdef9231d1b9`

The 1.8.4 mapped source confirms the same topology: `Map<Integer, Set<AttributeModifier>>` backed by `Maps.newHashMap`, three operation `Sets.newHashSet` instances, operation-specific getters/insertion, a separate aggregate union HashSet, a category-local HashSet copy, nearest-parent-to-root `addAll`, the same 0→1→2 calculation loops, and UUID-only modifier equality/hash code. The existing 1.8.2–1.8.4 report records the relevant sources byte-identical over those releases. The raw .0/.1 disassembly now directly verifies the same operation-order container algorithm at the two previously unbound endpoints.

## Reproduction boundary

The historical sequence is not simply the current runtime's public `getModifiers()` order, nor the current calculation's per-operation map-values order. Reproduction of a historical category requires the historical modifier UUID hash/equality semantics, a set built from that category's local modifiers, and the same parent-chain merge order before iteration. Exact iteration also depends on the `HashSet` implementation/runtime and the collection insertion history; those runtime details are outside this bytecode-only supplement. The class sources give no source-supported mapping from an arbitrary modern identifier to an old UUID, so a modern modifier without an established UUID counterpart has no historical bucket identity or order here.

This supplement is source/bytecode evidence only. It is not runtime parity evidence and has not received a separate independent audit.
