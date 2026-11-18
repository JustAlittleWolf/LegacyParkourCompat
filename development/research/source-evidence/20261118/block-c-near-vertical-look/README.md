# Near-vertical glide look-vector evidence (2026-11-18)

## Verified checkpoint

This checkpoint preserves eleven exact stable alias comparisons from 1.9 through 1.12.2 plus a separate 1.12.2→26.2 destination-endpoint witness. The scope is the ordinary finite near-vertical player input feeding the actual look vector during already-active, dry chest-Elytra movement. It does not cover activation, final glide handling, other branches, gameplay/runtime, product behavior, or general parity.

| Pair | Bounded result / qualification | Report SHA-256 | Verdict SHA-256 |
|---|---|---|---|
| 1.9→1.9.1 | Accepted bounded no-difference through the first move; near-vertical finite negative pitch retains the old lookup and denominator path. | `e6be06d5666bfc27b902ccd947b94fb36c1ee1cacf91843b3882323d1cea2600` | `56ed04890447e323087cbdb6c989dbd77286a506324e96ef13ae4e70cc74d238` |
| 1.9.1→1.9.2 | Accepted bounded no-difference for ordinary dry already-active look construction, trig lookup and first movement. | `3bc17d57ddb47563fcda86fe19c97152edeabc5094eefd751f8d905234eb71c2` | `d88f98712fee692265597447d8913519daa8d9972b1f31fd5ff3ce47037ef6a3` |
| 1.9.2→1.9.3 | Split verdict: source slice no-difference accepted, but audit corrects the report's literal sample lookup index to 32767; stated `j > 0.0` and `i < 0.0F` gates still hold. | `238a53ad9128473f7dca443011f5c1c96361de566bd8d6913734a0ac69beb53b` | `2832c5b78b20dfed9f696c101d8c61a209d29517a9ab9d66c02d5cf196e56cea` |
| 1.9.3→1.9.4 | Accepted for the stated ordinary dry active look-vector source slice. | `9bbd41e786c522e60b7c01152b7732ac90f185f681f70487a281b165cd738870` | `7011d5141f9c01c6269179da7b2567f2ca378817eb4519a477c524705188e41d` |
| 1.10→1.10.1 | Accepted bounded no-difference for ordinary dry, already-active near-vertical Elytra look and movement slice. | `15b8a50bd8f11c2e6399570c676e23381db4e28d18c8d62c205999b58abea205` | `16f1efec453684ef7383d96098dfe5a852173a4c9742d4ed7347de2e3cfb164f` |
| 1.10.1→1.10.2 | Accepted bounded no-difference for the named near-vertical look slice. | `aba719842bdc89f48fa4ef9788683fa8b56f4c41154ef1284ac246a4ae6c6e83` | `fbff63488add2b8542ddee823a2415022181edf5df02388c3320cf563d35335d` |
| 1.11→1.11.1 | Accepted bounded source comparison for the same active dry near-vertical look operation. | `022baace6a8cd0220798ab694f904acab49e521bec1534344c0d64d798a17a63` | `403eed609d5f61034c9cdd37a1f4a2cfe6b1b5589483477a43ae6ce863f03759` |
| 1.11.1→1.11.2 | Bounded no-difference confirmed. Local dispatch uses actual player pitch/yaw, not the base `headYaw` path; the verdict closes that provenance gap. | `42cf9929340fd9b6fdecc7e24eb405fe5f57e9df3277b6cf079d80a85dfab8ed` | `d32f89a9a64b9c7567b993930dc3e8b9c94ea9a83804179d75dbfb778993723f` |
| 1.11.2→1.12 | Core bounded no-difference accepted; evidence correction identifies the local pitch/yaw override rather than base headYaw as the effective path. | `e81591bd6b77f6aaa4f4a9f4773459e16e9812a3f04ea252b5db6ef397deb6c2` | `0aa65661bfc6b0fde62fa1d1bb8614653df1844b49b391e4e8d3fde9c39bb873` |
| 1.12→1.12.1 | Accepted bounded no-difference for ordinary dry already-active Elytra movement. | `4b2a53ebda3a6332b6990f395158760c5f0604ad78f5822ca3254754afdec9da` | `948b963172d503b8476953a3189173380a6b5cac9b0ac2ff534cfda2af492991` |
| 1.12.1→1.12.2 | Accepted bounded no-difference; verdict corrects the report's claim that canonical 1.12.1 hidden provenance is absent. | `ac9b2bc553da669aaeb18fca8dd6205aee693b85c6a028a58e7c1d68f5c6ad83` | `a309ba1e18856f685a97e7350ee9ee5a429e928ca4a639a794a84ee166f640f7` |
| 1.12.2→26.2 endpoint witness | Accepted exact near-vertical look-vector behavior and first active dry movement consumption. The provenance-resolution supplement is included; it revalidates the complete local source manifests/trees while noting the original raw artifact cache is unavailable, so raw binaries are not newly rehashed. | `152a5c403175787915ed6aa6b0d3e8414375e3346538418f7db25f82617b43a0` | `01d64143d13d841636385eccdacf99644f3e47b10bf47effcb7d8a477e6109ec` |

The existing 1.12.2→1.13 negative-pitch guard/trigonometry witness is archived separately at commit `b8ca33c5…`, under `development/research/source-evidence/20261118/block-c-middle-late-player-operation-closures/c-glide-1122-113-denominator-witness/`; it is referenced rather than duplicated here. That witness does not claim an old reachable divide-by-zero.

## Pending before final freeze

One alias source pair remains withheld pending a signed exact-input identity correction. The 1.9.4→1.10 report hashes to `3f9ef667c4d326a3adafd1e5b7f12edc920b630695651d8b4c7b15c701bb7651`, while its frozen verdict binds `3f9ef667c4d326a3adaf1d5e7bf12edc920b630695651d8b4c7b15c701bb7651`. The 1.10.2→1.11 report/verdict pair is now included with its unchanged files and signed correction `66518a21de050ff36987b7829c2cecd166dfc163ca353627f8e9807032da3cdd`, which binds report `8d28ad8a0d2037250462b350ddf3455284fd97b07e4fd52d0e1c4a433270cfef` to verdict `e7a2f41d72da392f5e7ed93a639291d96cc9d28410daf4a054bd4133cbe653d6`. 
The 1.12.2→26.2 provenance supplement is included at SHA-256 `84d0d1f7daa26ce7d674d69aac4fad7710ec53b9c460c8cf05242a03336ad432`; original raw artifact files remain unavailable. This commit is a verified subset checkpoint, not the final 13-alias archive.

Every included report and verdict is copied byte-for-byte; each verdict was checked against the exact report digest. No implementation, product parity, runtime, or full-pair coverage is claimed.