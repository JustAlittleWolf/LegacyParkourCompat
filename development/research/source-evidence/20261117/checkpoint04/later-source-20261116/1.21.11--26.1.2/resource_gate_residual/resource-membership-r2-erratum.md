# Resource packet citation correction r2

This immutable revision corrects only the two officialArtifactMetadata[].metadataSha1 fields in input-reference-r2.json. The original reference remains unchanged (SHA-256 $r1hash). The independent audit verdict udit/verdict.md (SHA-256 7dbd66218f81b8fb1043d9f1190cc1a103fd9e8918cf397d56553f56a9d41575) identifies the expected metadata-object SHA-1 values; both values were copied from that verdict and statically checked against it:

- 1.21.11: 718cd84b4912691fe5de829aab6876a4b7f6c5c4
- 26.1.2: 8cca2c0efc47ef22351e5073bb1f5e7ab339279b

All other packet fields are unchanged from r1 after normalizing only those two corrected fields. JAR pins, tag payloads, source identities/consumer bindings and archive verification contract remain unchanged. This changes no source or movement conclusion; reuse prior acceptance for all unchanged resource/source checks and independently audit only these citation bindings.

Corrected input reference SHA-256: $r2hash.