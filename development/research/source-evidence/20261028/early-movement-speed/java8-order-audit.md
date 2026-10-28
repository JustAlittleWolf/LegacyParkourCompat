# Independent audit: bounded Java 8 modifier order

## Verdict

**ACCEPT, within the stated reference-implementation scope.** For the exact built-in Speed, Slowness, and sprint operation-2 UUIDs, the supplement's ordering derivation is correct for OpenJDK 8u412 (`jdk8u412-b08`) and a historical movement-speed modifier set containing only those known identities. Every subset uses distinct slots in a 16-entry table, so insertion/removal history does not change iteration order. The result is Slowness, sprint, Speed, followed by omission of identities absent from the subset.

The supplement correctly limits its claim to that reference implementation and known-only history. Minecraft launcher metadata requires `jre-legacy` major 8 but supplies no vendor or update pin; this evidence does not establish iteration order for every Java 8 VM. Unknown or unproven modifier identities must remain on the native fallback.

## Inputs rebound

The reviewed supplement is `.native-source/20261028/1818-speed-java8-order/verification.md`, SHA-256 `f9427e6ca95f4e128af1ba299d881a74289e665ac1d64520cddf4dbe65ab5005`; its sidecar contains the same digest. I recomputed the hashes for the linked source inputs and they match:

- Raw bytecode report: `.native-source/20261028/1818-speed-boundary-raw-bytecode/REPORT.md`, `1edb89f388e395d96ed75df2c86066b57f7fdd1c07fb5518e260539d2a642032`.
- Raw modifier-order supplement: `.native-source/20261028/1818-speed-boundary-raw-bytecode/modifier-order-supplement.md`, `aab4daf0970fb7e6342240fb91a2eac5f51ba3f2c1eed57329de4da9e9c14d2a`.
- Its independent audit: `.native-source/20261028/1818-speed-boundary-raw-bytecode-audit/modifier-order-audit.md`, `ad2e8307a0b97b3b6bc6eb553e43402bdf8c6d963ef90a0413f76ec58179883e`.
- Earlier 1.8.2–1.8.4 source report and audit: `3ab8af23b8f8bd76d3e7c0e0d15d288f427916b8778d696bba2c1b6beedfe411` and `be3d4127cbdc0b6031bd933cf470ec34a85678e8c2418621147baf8362d1ed99`.

Read-only checks in the primary cache confirmed that the local 1.8 and 1.8.1 `version.json` hashes are respectively `8be392d3e8b3e61500b60eb141de56519299069c9169081483ea5aae9fa9d593` and `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149`. Both declare `javaVersion.component = jre-legacy`, `majorVersion = 8`, and Guava `17.0`. Their local `client.jar` SHA-256 values are `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe8` and `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd`; each SHA-1 matches its `downloads.client.sha1`. The shared Guava artifact SHA-256 is `8c36a80ea613d0b6b8040a17cf837c5bbe3677bc1b06a058a6c174fdb787ebbc`, matching both local artifact pins and the JSON's `com.google.guava:guava:17.0` entry.

## Independent checks

Read-only `javap -p -c -s` against the exact hash-bound raw client JARs reconfirmed the relevant collection topology in `ye` (1.8) and `yh` (1.8.1): the constructor initializes operation buckets with Guava `Sets.newHashSet()`, and the category helper passes its `getModifiers(int)` `Collection` to `Sets.newHashSet(Iterable)`. The helper's local set is a `HashSet`; the calculation processes operations 0, 1, then 2. The raw `afs`/`afr` static initializers pass `null` as the parent of `generic.movementSpeed`, so the supported built-in movement-speed case has no parent category to merge.

The raw `wp`/`wr` potion initialization and `xm`/`xo` sprint initialization contain the same three UUID strings shown in the supplement. Each constructor passes operation integer 2. The raw data therefore binds the known set to Speed `91AEAA56-376B-4498-935B-2F7F68070635`, Slowness `7107DE5E-7CE8-4030-940E-514C1F160890`, and sprint `662A6B8D-DA3E-4C1C-8813-96EA6097278D`.

The pinned Guava 17.0 `Sets.newHashSet(Iterable)` source takes its `Collection` branch for the operation bucket and calls `new HashSet(Collection)`; the bytecode descriptor observed in the Minecraft helper is the Iterable overload. The no-argument Guava overload calls `new HashSet()`. The pinned OpenJDK 8u412 `HashSet(Collection)` constructor uses a minimum initial capacity of 16 and then `addAll`; with three or fewer entries, the OpenJDK `HashMap` table remains length 16 and its threshold is 12. The pinned HashMap source uses `h ^ (h >>> 16)`, masks into a power-of-two table, and its iterator advances from slot zero to higher slots. The pinned UUID source computes the hash by xor-folding the two 64-bit UUID halves.

I independently recomputed each UUID hash and spread hash with the OpenJDK 8 formulas:

| Modifier | UUID hash | spread hash | 16-slot bucket |
|---|---:|---:|---:|
| Slowness | `0x86f7c7b2` | `0x86f74145` | 5 |
| sprint | `0x549096f6` | `0x5490c266` | 6 |
| Speed | `0x5d99c784` | `0x5d999a1d` | 13 |

Because the slots are distinct, every subset has fixed relative bucket order. The category copy uses the same minimum capacity, and its source iteration order does not alter these distinct destination slots. Neither initial insertion order nor removal/reinsertion can cause a resize with at most three supported identities. The static hash values independently match the supplement.

The pinned primary references cited by the supplement are OpenJDK `jdk8u412-b08` `HashMap.java`, `HashSet.java`, and `UUID.java`, plus Guava 17.0 `Sets.java`. The source report accurately describes these as reference-source behavior rather than a Java Collections API guarantee. No Java runtime was launched, no class was executed, and no test, game, or build was run.

## Limits retained

This audit accepts only the collection-order derivation. It does not map arbitrary modern identifiers to old UUIDs, establish arbitrary server/equipment modifier histories, prove every possible modifier source contains only the three known IDs, or claim runtime parity. Product code must use native fallback whenever that known-only condition cannot be established.
