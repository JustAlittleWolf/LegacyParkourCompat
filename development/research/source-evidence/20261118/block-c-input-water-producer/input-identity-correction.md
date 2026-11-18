# Independent identity erratum: 1.14.3 local-player source pin

This erratum supplements the frozen source comparison and independent verdict without changing either artifact.

**Bound inputs**

- Comparison draft raw SHA-256: `3a2bce15a218d961779a90c78eeca0d482ca99e8294265e20301f5e39a58f75c`
- Independent verdict raw SHA-256: `5980a123d975e140bcacc2b032b4d165fa407be1674cec44108d95b350b38f0e`

**Correction and verification**

The comparison's listed 1.14.3 Yarn `ClientPlayerEntity.java` SHA-256 contains a one-character typo (65 hexadecimal characters), so the earlier verdict's statement that every listed source hash matched was too broad. I independently recomputed the canonical file's raw SHA-256:

`decompiled_minecraft/1.14.3/yarn/net/minecraft/client/network/ClientPlayerEntity.java`

`28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`

This 64-character digest matches the entry for `net/minecraft/client/network/ClientPlayerEntity.java` in `decompiled_minecraft/1.14.3/yarn/.decompile-provenance.json` (whose raw SHA-256 is `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`). Thus the canonical client source pin is verified; the malformed digest was only a transcription error in the comparison.

**Verdict effect**

The bounded acceptance remains unchanged: the producer comparison and tick-order conclusion are still tied to the same canonical source, now with its corrected raw digest. No claim is added or broadened. The loaded-region, fluid-current, callback, separate-submersion, and full sprint-input gates recorded in the original verdict remain open or excluded.

**Signed:** Independent source-audit worker `c_input_water_gate_audit`  
**Disposition:** Identity correction only; frozen comparison and verdict preserved.

