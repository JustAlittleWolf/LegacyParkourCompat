# Source supplement: bounded Java 8 order for standard 1.8 movement-speed modifiers

## Verdict and scope

For the exact built-in Speed, Slowness and sprint modifiers in Minecraft 1.8 and 1.8.1, and the 1.8.2–1.8.4 source-identical operation, the historical operation-2 collection contains at most three known UUIDs with distinct Java 8 hash buckets. Under the Java 8 `HashMap`/`HashSet` implementation referenced below, every subset therefore iterates in the fixed bucket order Slowness, sprint, Speed, independent of insertion order. No resize or collision occurs. This closes the runtime-order concern for these three standard identities only when the movement-speed modifier history contains no other identity.

The bound launcher metadata specifies `jre-legacy`, major version 8, but does not pin a vendor or update number. The implementation derivation below is explicitly a Java 8 reference implementation derivation against the immutable OpenJDK tag `jdk8u412-b08`; it does not claim the Java Collections API guarantees iteration order across all Java 8 implementations or updates. Unknown modifiers, arbitrary server/equipment modifiers, or any history that cannot be proven to contain only these identities must keep the existing native fallback.

## Bound Minecraft and Guava inputs

The raw client, mapping and source identities are those in the accepted source supplements:

- `.native-source/20261028/1818-speed-boundary-raw-bytecode/REPORT.md`, SHA-256 `1edb89f388e395d96ed75df2c86066b57f7fdd1c07fb5518e260539d2a642032`.
- `.native-source/20261028/1818-speed-boundary-raw-bytecode/modifier-order-supplement.md`, SHA-256 `aab4daf0970fb7e6342240fb91a2eac5f51ba3f2c1eed57329de4da9e9c14d2a`.
- `.native-source/20261028/1818-speed-boundary-raw-bytecode-audit/modifier-order-audit.md`, SHA-256 `ad2e8307a0b97b3b6bc6eb553e43402bdf8c6d963ef90a0413f76ec58179883e`.
- `.native-source/20261028/1818-speed-boundary/REPORT.md`, SHA-256 `3ab8af23b8f8bd76d3e7c0e0d15d288f427916b8778d696bba2c1b6beedfe411`; its independent source audit is `.native-source/20261028/1818-speed-boundary-audit/audit.md`.

The exact 1.8 and 1.8.1 launcher `version.json` files rehash to `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593` and `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149`. Both specify `javaVersion: {component: "jre-legacy", majorVersion: 8}` and `com.google.guava:guava:17.0`. The launcher library artifact URL is `https://libraries.minecraft.net/com/google/guava/guava/17.0/guava-17.0.jar`. Its SHA-1 is `9c6ef172e8de35fd8d4d8783e4821e57cdef7445`; the cached JAR was independently SHA-256 hashed as `8c36a80ea613d0b6b8040a17cf837c5bbe3677bc1b06a058a6c174fdb787ebbc` (identical artifact at both releases).

Read-only `javap -p -c` against that pinned Guava 17.0 JAR confirms the relevant overloads:

1. `Sets.newHashSet()` invokes the no-argument `java.util.HashSet` constructor for each initially empty operation bucket.
2. `Sets.newHashSet(Iterable)` checks `Iterable instanceof Collection`; the historical category-copy argument is the operation `Set`, hence this branch invokes `new HashSet(Collection)`. It does not call the varargs or `newHashSetWithExpectedSize` overload.
3. In the pinned OpenJDK 8 `HashSet(Collection)` source, the backing `HashMap` initial capacity is `max((int)(c.size() / .75f) + 1, 16)`, then `addAll(c)`. For 0–3 supported elements that is at least 16; Java 8 `HashMap` rounds to a power-of-two table of length 16 on first insertion. Its threshold is 12, so a set of at most three unique known elements cannot resize.

The source method references are the immutable OpenJDK 8 update tag:

- [HashMap.java at jdk8u412-b08](https://github.com/openjdk/jdk8u/blob/jdk8u412-b08/jdk/src/share/classes/java/util/HashMap.java): default capacity 16, spread hash `h ^ (h >>> 16)`, table index `(n - 1) & hash`, and iterator's ascending table-slot scan.
- [HashSet.java at jdk8u412-b08](https://github.com/openjdk/jdk8u/blob/jdk8u412-b08/jdk/src/share/classes/java/util/HashSet.java): no-argument and collection constructors, including the minimum-16 sizing rule.
- [UUID.java at jdk8u412-b08](https://github.com/openjdk/jdk8u/blob/jdk8u412-b08/jdk/src/share/classes/java/util/UUID.java): `hashCode()` folds the two halves of the UUID with xor.

No runtime or class was executed. Java hash/bucket values below are static arithmetic from those source bodies.

## Exact historical identities and operation

The raw pinned 1.8 (`StatusEffect` official class `wp`, `LivingEntity` `xm`) and 1.8.1 (`wr`, `xo`) bytecode independently contains these same three UUID strings. The potion constructors pass operation integer 2; the sprint modifier constructor also passes operation 2. The already provenance-bound Feather 1.8.4 `StatusEffect.java` and `LivingEntity.java` source bodies confirm the names, UUIDs, values and operation: `StatusEffect.SPEED` is `91AEAA56-376B-4498-935B-2F7F68070635`, Slowness is `7107DE5E-7CE8-4030-940E-514C1F160890`, and sprint is `662A6B8D-DA3E-4C1C-8813-96EA6097278D`. The corresponding source file hashes are StatusEffect `f9bb4d1839337229cb6f6d8dc9ae17a49cfad757a42388e00abbaa68be2b21a3`, LivingEntity `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`, and EntityAttributes `945f39931934b378359d428a2f0fb0b57c97bb2e162dcec7e5d8562efaf663aa`.

Raw 1.8 and 1.8.1 bytecode also constructs `MOVEMENT_SPEED` with a null parent argument. Its mapped 1.8.4 declaration likewise passes `null`, and relevant attribute-instance source bodies are byte-identical across 1.8.2–1.8.4. Thus no inherited movement-speed modifier category is merged from an ancestor in this bounded built-in case. The attribute starts with three empty category sets; only these three standard IDs are in the supported known-only history.

## Static bucket derivation

OpenJDK 8 `UUID.hashCode()` first computes `long hilo = mostSignificantBits ^ leastSignificantBits`, then returns `(int)(hilo >>> 32) ^ (int)hilo`. `HashMap.hash()` then xors that 32-bit result with its unsigned right shift by 16, and a 16-slot table uses the low four bits.

| Modifier | UUID | UUID hashCode | spread hash | slot (`spread & 15`) |
|---|---|---:|---:|---:|
| Slowness | `7107DE5E-7CE8-4030-940E-514C1F160890` | `0x86f7c7b2` | `0x86f74145` | 5 |
| sprint | `662A6B8D-DA3E-4C1C-8813-96EA6097278D` | `0x549096f6` | `0x5490c266` | 6 |
| Speed | `91AEAA56-376B-4498-935B-2F7F68070635` | `0x5d99c784` | `0x5d999a1d` | 13 |

All three slots differ. OpenJDK 8 HashMap iteration visits table slots in ascending index, so any subset follows the fixed relative order Slowness → sprint → Speed. With at most three supported elements, the category-copy collection has capacity 16 and never crosses resize threshold 12. Because no two supported UUIDs share a slot, the order does not depend on Set insertion order, add/remove order, or the local modifier Set's traversal order. `MOVEMENT_SPEED` parent is null, so parent-chain merge does not introduce additional members.

## Limits

This only settles ordering of the three exact built-in operation-2 UUIDs on the specified reference implementation for the known-only movement-speed history. It does not give an old UUID to arbitrary modern identifiers, establish server/equipment modifier identity, cover unknown historical custom modifiers, or claim Java API-level ordering guarantees. When any unsupported identity or history is present, native fallback remains required. This is source/runtime-library analysis only, not runtime parity evidence.


