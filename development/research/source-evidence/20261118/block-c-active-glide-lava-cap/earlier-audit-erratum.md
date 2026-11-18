# Source-hash erratum r2: 1.20.2 Mojmap LivingEntity

Correct the 1.20.2 LivingEntity.java SHA-256 table value in both frozen comparison.md and the prior independent verdict.md. The originals remain unchanged; this addendum records the correction.

Canonical source path: decompiled_minecraft/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java
Correct canonical SHA-256: 5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828
The canonical hash matches its ready Mojmap source manifest entry. Ready marker identity: versionId=1.20.2, mapping=mojmap.
Unchanged Entity.java SHA-256: d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b
Ready-marker raw SHA-256: 5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f
Source-manifest raw SHA-256: e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887

## Frozen payload bindings

Earlier comparison.md raw SHA-256: 58204e94fb4c697bcfe0b7de87e965c7f7b0bd1a22c38ff0a08c0f176b0796a7
Earlier comparison-r2.md raw SHA-256: 932c220420a92aef908567b156832162e7bf20fb1882aa07ccedf04e945ff704
Prior verdict.md raw SHA-256: db5e5eac8a8d43c1db5c860d2534f407ae4d63552e32b8b4e4e1a0705e8e785a

Erratum input digest (SHA-256 over the exact seven identity lines below as UTF-8 with final LF): c2bcc79e99db61d00f6076fca6f95de8bf2ea241586cd14c566c3c4236094cfc

Exact seven-line identity block:
~~~text
canonical.decompiled_minecraft/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java=5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828
canonical.Entity.java=d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b
canonical.readyMarker=5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f
canonical.sourceManifest=e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887
frozen.comparison.md=58204e94fb4c697bcfe0b7de87e965c7f7b0bd1a22c38ff0a08c0f176b0796a7
frozen.comparison-r2.md=932c220420a92aef908567b156832162e7bf20fb1882aa07ccedf04e945ff704
prior.verdict.md=db5e5eac8a8d43c1db5c860d2534f407ae4d63552e32b8b4e4e1a0705e8e785a
~~~

Correction scope: the two frozen table cells only (comparison.md and verdict.md). Source behavior, provenance classification, acceptance, applicability and the prior verdict are unchanged. No profile alias or trajectory claim is added.
