# Independent audit: ATTACK_KNOCKBACK Java defaults r2

## Verdict

**ACCEPT.** The corrected report is complete for its bounded Java-default/supplier claim. It preserves the prior report's claims and limits and adds the missing source identity row for `AttributeMap.java`.

## Input identity

Reviewed report: `build/later-source-20261116/26.1.2--26.2/attack_knockback_code_defaults_revision_r2/coverage-r2.md`  
SHA-256: `bb738625928f034ad518d7607dd0c399c300780e65cca6d3a166a44814294398`

The prior audit bound the unchanged report body, source identities, and nine listed Java inputs to report r1 SHA `a98417172762ff9d14b87a5c20b202a8fee9cd8acc8dc0867aa8602ccec2e595`. Comparing r1 and r2 confirms the only content change is the added `AttributeMap.java` source table row.

## Corrected source binding

For both `decompiled_minecraft/26.1.2/unobfuscated` and `decompiled_minecraft/26.2/unobfuscated`, local `net/minecraft/world/entity/ai/attributes/AttributeMap.java` hashes to `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6`. The same digest appears for that relative path in each endpoint's `unobfuscated.sources.sha256` manifest. The inspected method at lines 62–65 returns the instantiated attribute value when present and otherwise falls back to the supplier value, as the report states.

## Reused checks and scope

The prior independent audit verified both endpoint ready/provenance/manifest identities and all nine previously listed Java file hashes, and accepted the bounded zero-default, player-supplier, and LocalPlayer inheritance claims. The r2 correction adds the missing hash binding for the sole source citation that was previously unbound. No claim or limitation changed. This remains a static declared-default result; it does not establish a live effective attribute value after packets, entity data, or modifiers, nor does it close pair coverage or runtime behavior.
