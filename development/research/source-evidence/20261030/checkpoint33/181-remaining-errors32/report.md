# Static diagnosis: 1.8.1 enum-switch failures after helper overlay

## Verdict

The “all 1.8.1 inner-class metadata was stripped” premise is supported for the pinned ordinary mapped archive and explains why an explicit helper-nesting repair was considered. It does **not**, by itself, explain the remaining 48 diagnostics. All 48 proof33 diagnostics say `available: {}`, so Vineflower captured no enum-switch assignment entries for each failure. The pinned 1.8.1 bytecode has those assignments. The failure is therefore upstream of enum-constant lookup: the helper `<clinit>` expression graph was not available/recognized by `SwitchHelper`, or the exact pre-Vineflower overlay did not satisfy the helper metadata / eligibility conditions.

The static evidence does not distinguish those last two alternatives because proof33 did not retain the transformed archive and failed publication removed its staging output. Do not add more nesting records or alter the zero-ERROR gate until the exact pre-Vineflower input for proof33 is inspectable.

## Evidence

- The pinned ordinary mapped 1.8.1 archive is SHA-256 `d2977e05623d692e2d73dacbd0d6328b9226f47d80f824711a6d5fe81df09001`. Prior raw-attribute inventory covers all 2,500 classes and finds no `InnerClasses` or `EnclosingMethod` attributes (only seven standard attribute names occur in that inventory). Raw client SHA-256 is `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd`.
- The accepted allowlist evidence binds 59 baseline diagnostics to 41 synthetic switch-helper classes. Its static `javap` review confirms each helper has `[I` synthetic static-final switch field(s), and a `<clinit>` that writes ordinal-derived enum mappings under `NoSuchFieldError` handlers. The comparator establishes that these correspond to Java nested helpers in the pinned 1.8.2 mapped archive.
- Independent read-only `javap -p -v` inspection of all 41 helpers in the ordinary mapped 1.8.1 jar finds all 41 satisfy Vineflower 1.12.0's `ClassesProcessor.shouldInitEarly` predicate: class is synthetic; there is exactly one method (`<clinit>`); and every field is `[I` with `ACC_STATIC | ACC_FINAL | ACC_SYNTHETIC`. Thus the helper bytecode shape itself is eligible for Vineflower's early wrapper initialization once linked into its owner as a nested class.
- `decompile-181-proof33.log` has 48 enum-switch errors and all 48 say `available: {}`; its SHA-256 is `2b8b8da40f72bb51d422ecbef5edb916cb5b0d81c079ff46430ac6c4502b2acf`. This is 48 diagnostics, not evidence that 48 distinct helpers failed; the established baseline binding table has 41 distinct helpers.
- Cached Vineflower 1.12.0 source jar SHA-256: `9ac4120c6afdf83bf0e798f3a28c56040d5e05fd32462681704539a244398da9`; binary jar SHA-256: `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`.

## Vineflower path implicated by the message

`SwitchHelper.isEnumArray` first identifies a candidate synthetic `[I` field. Its field branch then looks up that helper's `ClassNode`, obtains its class wrapper and `<clinit>` `MethodWrapper`, and only scans assignments when `wrapper.root != null`. It populates the switch map from array writes whose index is an invocation (the `ordinal()` call). If the map has no entry for the switch label, it emits the observed message with the current mapping contents.

`ClassesProcessor.initWrappers` has a deliberate early-init rule for nested synthetic helper classes. It initializes a nested class early only if it has one `<clinit>` method and every field is `[I` or `Z` and `static final synthetic`. All 41 pinned helpers meet that predicate. This narrows the next check to whether the actual overlay archive linked each helper under its intended owner before Vineflower loads/processes classes, and whether the wrappers/root for the relevant `<clinit>` are in place when the failing consumer is simplified. It does not prove those runtime states from the error log alone.

## Minimal next repair contract

1. Preserve/hash the exact transformed archive handed to Vineflower on the designated proof run; the failed staging archive was not found among retained sources. Do not regenerate it in this static-diagnosis task.
2. For the 48 diagnostics, map each failed method's `GETSTATIC [I` reference to its helper class and field in that exact archive. Check `this_class`, one-method `<clinit>`, field flags, `<clinit>` ordinal writes, the helper's `InnerClasses`/`EnclosingMethod`, and the reciprocal owner `InnerClasses` record against the accepted 1.8.2 comparator shape.
3. If any candidate fails those bytecode/metadata checks, repair only that evidenced discrepancy in the exact 41-pair allowlist, preserve existing archive delta gates, and rerun the owner's existing strict proof. If all checks pass, investigate Vineflower wrapper/root availability at `SwitchHelper.simplify`; only then consider a narrowly scoped decompiler-side ordering/eager-initialization fix for the switch helper. Keep zero ERROR and failed-method publication gates intact.

Do not infer that every missing `InnerClasses` relation in the 1.8.1 jar should be restored. This review proves neither arbitrary nested-class ownership nor the suitability of such a blanket restoration.

## Reproduction

`static_diagnosis.py` reads the allowlist and proof33 log, hashes the immutable inputs, checks all 41 helper class headers/fields with `javap`, and prints source line anchors from cached Vineflower sources. It does not launch Vineflower, modify archives/caches, or touch product source. The exact 1.8.1 helper input reported by the script is the ordinary mapped archive, not the unretained overlay output.
