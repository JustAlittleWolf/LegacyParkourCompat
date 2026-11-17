# Independent audit: client direct player responses

**Verdict: ACCEPT**

## Bound claim and evidence identity

This verdict accepts only the bounded claim that no changed 1.10.2→1.11 client play packet/event handler in the reviewed slice introduces an eligible direct local-player movement-state delta, and that the report's inventory of direct-response paths and changed-handler dispositions is adequate for this slice. It does not freeze the pair or establish implementation readiness or runtime parity.

Reviewed report: `dependencies/client-direct-player-responses.md`, SHA-256 `B923F7B8A3DEE7DBC279525D716E3AB3555C096DD1014BE037E5A9F3A8DAA906`.

Exact endpoints: 1.10.2 and 1.11, Ornithe Feather. The endpoint ready-marker hashes are `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` and `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45`; source-manifest hashes are `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` and `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`. The report's handler-file hashes were independently recomputed and match those manifests: `af3efba0a1d5a0824c29c4af4ac7f8e4484d1787a82208e4d72bcc65f957e656` (1.10.2) and `cef5d643eca0b32119571eff5201705cc8c99125f8266f763172202d89e3a40b` (1.11).

## Audit basis

I compared the endpoint `ClientPlayNetworkHandler.java` bodies and reviewed the reported direct player-response handlers, generic entity-to-local-player routes, and all changed handler bodies. The unchanged correction, velocity, explosion, and abilities receive paths are described with their local-player applicability gates. The changed bodies are appropriately dispositioned: the totem event only adds visual effects; passenger changes concern unresolved IDs; added entity types spawn entities other than the local player; and the remaining changes affect UI, inventory, block-entity payloads, or resource handling. The new item-pickup stack-count update applies to an `ItemEntity`, not player movement state. No reviewed changed handler writes or newly reaches local-player position, velocity, dimensions/pose, or movement flags.

The report distinguishes the server-side producers from client handling. I checked the cited correction producer's relative-coordinate packet construction and the listed tracking/explosion/ability response producers in the endpoint sources. The correction packet construction and relevant explosion payload path retain their response encoding; the tracking velocity branch concerns tracked entities, while the separate damaged-entity velocity send remains present. Server-side movement validation/physics changes are not accepted as client emulation findings under this claim.

The separate moving-piston report is not covered by the no-difference conclusion. Its accepted r2 report has SHA-256 `49ccaea0cc48e8bd8314d63d647c4c976febf4448e27dd8de9cb424fc556a18f`; the cited correction verdict limits it to conditional displacement and leaves moved-state shape, complete clipping dependency, map reachability, and net trajectory open.

## Scope limits

The acceptance excludes damage and fall-damage calculation, bed-entry eligibility, vehicle physics, server policy/physics, and new 1.11-only entity movement. It closes only the external client packet-handler direct-response slice. The broader pair coverage remains partial, including the independent tick, collision-provider, fluid, effects/equipment and other dependency gates recorded in `coverage.md`.
