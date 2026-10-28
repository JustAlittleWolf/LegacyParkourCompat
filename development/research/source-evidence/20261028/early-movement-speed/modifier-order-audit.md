# Independent audit: 1.8.0–1.8.1 modifier collection order

## Verdict

**Confirmed for the bounded bytecode claim.** The supplement’s exact input identities rebind to the local read-only cache, the Tiny class/member crosswalk agrees with the pinned client classes, and independent javap -p -c -s inspection reproduces the bucket construction, operation selection, parent merge, fixed calculation category sequence, aggregate union, and UUID equality/hash behavior in both releases. The compared 1.8.4 Feather source bodies are present with the cited hashes and show the same topology.

No material discrepancy was found. The supplement’s exclusions are necessary: these inputs do not establish a Java runtime’s concrete HashSet iteration order or an arbitrary modern identifier to historical UUID correspondence.

## Evidence rebind

Local inputs were under D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache. SHA-256 was recomputed directly for each version JSON, client JAR, Feather Gen2 mergedv2 JAR, Tiny file, and Feather mapping metadata JSON. All match the pins in the supplement. Each client JAR’s SHA-1 also matches its downloads.client.sha1 in that exact local version JSON. The Tiny file copied beside each mapping artifact is byte-for-byte hash-equal to the artifact’s embedded mappings/mappings.tiny.

The exact SHA-256 values and paths are recorded in [evidence-rebind.json](evidence-rebind.json). Both Tiny files have namespace order official, intermediary, named; the mapping metadata selects stable build.2.

Class crosswalks verified in the pinned Tiny files:

| Named class | 1.8 official → intermediary | 1.8.1 official → intermediary |
|---|---|---|
| ModifiableEntityAttributeInstance | ye → net/minecraft/unmapped/C_13795309 | yh → net/minecraft/unmapped/C_13795309 |
| EntityAttributeInstance | xz → net/minecraft/unmapped/C_87137557 | yc → net/minecraft/unmapped/C_87137557 |
| AbstractEntityAttributeContainer | yc → net/minecraft/unmapped/C_33220211 | yf → net/minecraft/unmapped/C_33220211 |
| AttributeModifier | ya → net/minecraft/unmapped/C_85460302 | yd → net/minecraft/unmapped/C_85460302 |

The Tiny member rows map b(I)Collection on each ModifiableEntityAttributeInstance to getAppliedModifiers(int). On the EntityAttributeInstance interface, the distinct descriptors (I)Collection a and ()Collection c both map to getModifiers, confirming that operation-specific access and aggregate access are separate methods.

## Bytecode independently reproduced

Using Eclipse Adoptium javap 25.0.3 with -p -c -s against only the two pinned client JARs:

- Each ModifiableEntityAttributeInstance constructor initializes the operation map and related maps with Guava Maps.newHashMap(). Its loop indexes 0, 1, and 2 and stores a fresh Sets.newHashSet() in each operation bucket.
- getModifiers(int) returns the map entry for that operation. addModifier obtains the operation from the modifier and inserts it into the corresponding bucket. UUID duplication is checked through the UUID-indexed map.
- Aggregate getModifiers() constructs a separate empty HashSet, loops through operation buckets 0–2, and adds their members. It is not the collection passed to the three calculation loops.
- The calculation method invokes its category helper with 0, then 1, then 2. It iterates each returned collection before advancing to the next operation, retaining the same arithmetic stages seen in the 1.8.4 source.
- The helper begins with Sets.newHashSet(localModifiersForOperation). It then starts at the attribute’s immediate parent, resolves that parent’s instance through the container, adds that instance’s same-operation modifiers when present, advances to the parent’s parent, and repeats to the root. It returns that merged set to the calculation method. Set equality therefore also deduplicates equal UUIDs encountered in the local or ancestor collections.
- AttributeModifier.equals first handles reference identity and requires the same runtime class; for matching classes it compares the UUID field with UUID.equals. hashCode returns the UUID field’s hash (or zero when null). Amount, operation, name, and serialization flag are not read by those identity methods. The explicit-UUID constructor stores its UUID argument. The convenience constructor obtains a UUID from the random helper and delegates to the explicit constructor.

The constructor, bucket getter, add operation, calculation method, parent helper, and modifier identity methods have matching structures in 1.8 and 1.8.1; raw class and owner names differ as expected. No classes were executed.

## Comparison with the bound 1.8.4 source

The cited source report .native-source/20261028/1818-speed-boundary/REPORT.md rehashes to 3ab8af23b8f8bd76d3e7c0e0d15d288f427916b8778d696bba2c1b6beedfe411. Its 1.8.4 provenance file rehashes to b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00, matching the provenance identity in that report. Independently hashed source files match the report’s source-map values:

- ModifiableEntityAttributeInstance.java: 3a8ddba11c80ee90640a1b0b85fd498d5e557459ed5b8bb64ea6a30ab7a0157d
- EntityAttributeInstance.java: 358d9b031fe281ab7837d3ae70d46654b0704db1e8c199234ba0bfd68a19692b
- AttributeModifier.java: ba990c7b8a57787a903a6083f373fe1180f2f9ee52e27c1a9984fdef9231d1b9

Those source bodies confirm per-operation Set buckets, a separate aggregate HashSet, operation-selected insertion, a category-local HashSet copy merged with same-operation parent buckets toward the root, fixed 0→1→2 calculation stages, and UUID-only modifier equality/hash code. The raw 1.8.0/1.8.1 observations therefore agree with the exact 1.8.4 source over this bounded operation.

## Limits retained

- Bytecode proves use and construction of HashSet collections, not their concrete iteration order for a specific Java runtime. It does not establish JDK version, table sizing/layout, or a stable ordered sequence for the modifiers.
- The source and bytecode expose UUID as the historical modifier identity, but provide no mapping from an arbitrary modern identifier to an old UUID. No such correspondence is inferred here.
- The evidence does not claim that arbitrary modern-only modifiers have historical UUID counterparts, nor does it establish runtime movement parity.

This was a read-only source audit. No cache/source/product files were generated or modified; no classes, builds, tests, Gradle tasks, game, TAS, Gym, or server were launched.
