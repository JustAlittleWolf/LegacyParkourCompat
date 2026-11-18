# Independent audit verdict: 1.21.7 → 1.21.8 modifier lifecycle slice

## Verdict

**Supported bounded no-difference result; consumer inventory needs one explicit follow-up.** I independently verified the report's exact-source identity and the paired Java payloads. The examined effect attach/update/remove, equipment modifier refresh/removal, and listed movement attribute paths show no source delta. The report does not establish full pair coverage or full modifier-default coverage, correctly leaving packaged data unresolved. Its direct movement consumer inventory omits `STEP_HEIGHT`, so that inventory is not complete as written; the identical enclosing `LivingEntity.java` file means this omission does not overturn the no-difference conclusion for that consumer.

## Input identity

- Audited input: `c-queue-1217-1218-modifier-lifecycle/report.md`, SHA-256 `c47272e262ec2fb93bd59cf3510ef787dd949ced09df6e1e42866ee713d97bc2`.
- Pair: canonical local `decompiled_minecraft/1.21.7/mojmap/` and `decompiled_minecraft/1.21.8/mojmap/`, Mojmap.
- 1.21.7 `.decompile-provenance.json` records exact 1.21.7 metadata/client inputs; its source-hash map matches all eight audited Java files.
- 1.21.8 `mojmap.ready.json` identifies version 1.21.8, Mojmap, 6,105 source files and source manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`. Recomputed SHA-256 of the local `mojmap.sources.sha256` matches that marker. The adjacent `mojmap.provenance.json` identifies the exact decompile run. Every audited 1.21.8 Java payload hash matches this manifest.

## Audited bounds and results

- `LivingEntity.java`: effect acceptance/replacement (`addEffect`, `forceAddEffect`), attribute application/update/removal callbacks, equipment slot collection and modifier replacement, old equipment modifier removal, glider eligibility, sprint modifier path, direct movement reads, and step-height read. The exact file is byte-identical across both versions (SHA-256 `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`). The checked callback gates and remove/add order are unchanged.
- `MobEffect.java`, `AttributeInstance.java`, `MobEffects.java`, `Attributes.java`, `ItemStack.java`, `Player.java`, and `LocalPlayer.java`: all cited files are byte-identical across the pair; each side's local provenance/manifest hashes match the file. This supports the report's effect modifier ID replacement semantics, Speed/Slowness modifier constants and operation, equipment modifier enumeration order, and cited player movement readers.
- Reported readers include movement speed, sneaking speed, movement efficiency, jump strength, gravity and water movement efficiency. The bounded audit also found `LivingEntity.getStepHeight()` reading `Attributes.STEP_HEIGHT` at line 3652, initialized on living entities at line 325 and used in player movement. This relevant consumer was not enumerated in the report. It is inside the byte-identical `LivingEntity.java`, so no 1.21.7→1.21.8 code change is present in that path.
- `AbstractClientPlayer.getFieldOfViewModifier()` also reads movement speed, but this is a rendering/FOV consumer and is excluded by project scope; it does not expand the accepted movement inventory.
- Exact canonical decompiled roots contain no packaged enchantment/data JSON resources: 1.21.7 has only the class-local provenance JSON, and 1.21.8 has no JSON under its source tree. Thus enchantment defaults and other resource-defined modifier inputs remain unverified in this evidence set. The report appropriately does not claim a complete no-difference result for those inputs.

## Review outcome and bounds

No concrete 1.21.7→1.21.8 modifier lifecycle or listed direct movement consumer difference was found in this slice. Retain the report's partial-pair status. Before treating its consumer inventory as complete, explicitly close the `STEP_HEIGHT` reader and its relevant modifier/default inputs; source identity already establishes unchanged Java behavior. This is an independent bounded audit, not a full-pair coverage freeze, data-resource audit, or runtime parity result.

Audited source bounds: the eight exact files named above under `decompiled_minecraft/{1.21.7,1.21.8}/mojmap/`; direct relevant `LivingEntity` slices at lines 982–1000, 1013–1022, 1044–1089, 2133–2140, 2192–2202, 2235–2238, 2301–2315, 2637–2675, 3523–3531, 3652, and 3678–3685; direct `MobEffect`, `AttributeInstance`, `MobEffects`, `Attributes`, `ItemStack`, `Player`, and `LocalPlayer` slices identified in the input report.
