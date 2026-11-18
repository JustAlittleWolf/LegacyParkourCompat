# B04 horizontal restitution corrective receipt

The erratum's 63-character original-audit digest omits the `e` immediately before `ab7`. SHA-256 over the exact committed file bytes gives these identities.

- Original audit `09d16d437af2a4a3f6cc13d1890d9dafdc314cc7:development/research/source-evidence/20261118/block-b-modern-collision/b04-horizontal-restitution-audit.md` has SHA-256 `f885e5a742105dc43e25428e2eab7a5594d30c779e24de543c6106efe439a5f3`.
- Manifest erratum `58b766c9305a4244aabce6b388c4ed227e9309af:development/research/source-evidence/20261118/block-b-adjacent-movement/b04-restitution/erratum.md` has SHA-256 `4e6e90ae4f659398190d87112b090e005ba76568bceae31456b42e66f0363a38`.
- The 26.1.2 source manifest `decompiled_minecraft/26.1.2/unobfuscated.sources.sha256` has SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, matching `unobfuscated.ready.json` for that release.

The audit verdicts still apply only to the fixed-profile local player's clipped horizontal axes. Both 26.1 to 26.1.1 and 26.1.1 to 26.1.2 retain clipped-axis velocity zeroing. The 26.2 control shows restitution, with positive effective bounciness required for nonzero rebound. The introduction boundary within 26.1.2 to 26.2, full-pair coverage, and independent tick-graph closure remain open.
