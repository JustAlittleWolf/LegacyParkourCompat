# Independent acceptance of source correction

## Signed verdict

**ACCEPTED.** The addendum closes the grounded stale-flag objection from my original audit. It correctly distinguishes the local value computed by `LivingEntity.updateFallFlying` from shared flag 7: on the client, the false result is not written back, so an already-true shared bit remains available to `isFallFlying()` and can select the glide branch before the existing post-movement `Player.travel` clear. Its claim is appropriately conditional on a grounded local-client state entering the sequence with bit 7 already true; it does not claim that this state is common or that normal scheduling produces it.

The separate ability-toggle claim is also correctly scoped. 1.20.5 invokes `jumpFromGround()` when enabling flight while grounded before `super.aiStep()`; this adds a jump impulse but does not establish a new glide predicate or move the clear. The original finding that the airborne glide branch can execute before the direct flag-7 clear, with fall-distance reset before the clear, remains valid. I found no remaining disagreement within the bounded source slice.

## Binding identities

- Original source report SHA-256: `4f54e836291863c44ed5c0bdddc802ad0c90d4886895537d88434be7c9762fb9`
- Original independent audit verdict SHA-256: `6f5ec5d670ac1e9705eca2a2188560ea3383fa0262529c246f1408be2ace1ddb`
- Source correction addendum SHA-256: `6a157c886cfdd5d9db2b617fd8c602e5094f4094db8af92d904dfadd69f1698`

The addendum's two explicit bindings match the original report and audit verdict identities above. Its underlying exact local source identities remain the Mojmap manifests and Java-file hashes listed in those bound artifacts. This acceptance is static source review only; it does not establish runtime frequency or movement parity outside this slice.

Signed: independent source auditor.
