# Coordinator handoff — 2026-10-08

This file is for the new coordinator only. It mixes lane metadata; do not give it or old-chat-checkpoints.json to normal source discovery or blind source reviewers.

## Authority and outcome

The user explicitly requests a fresh coordinator chat, fresh replacement worker chats, and at most 15 concurrent agents. Use separate Codex app chats with model gpt-6-luna and thinking high for delegated work. Do not fork the old conversations. Do not tell workers their model or relay unrelated user/error history. Count all active roles; conservatively keep coordinator plus workers <=15. Old workers received checkpoint-and-stop instructions; inspect their final state before replacements start. Do not reactivate old workers for substantive work.

Goal: compare exact vanilla source across major/content releases from 1.8.9 to native26.2, independently audit Minecraft Wiki and MCPK Wiki, find all eligible player-movement/collision changes, implement each specific minimal delta, independently review, build with tests disabled, and MERGE ALL completed accepted work into local main. Incremental integration while discovery continues is explicitly authorized. Perfect historical parity is the long-term goal; never claim it from builds or partial inventories.

Read global C:/Users/Wolfi/.codex/AGENTS.md, current repository AGENTS.md, README.md, workflows/movement-discovery/README.md and implementation workflow. Existing root checkout: C:/Users/Wolfi/.codex/worktrees/9539/LegacyParkourCompat, branch feat/movement-completeness-final. Primary checkout: D:/Javastuff/LegacyParkourCompat, main last verified002137b227676caea77f6832b9f4c8d0b6200bff. Reverify before integration. Project id26d9089a-072a-418a-949d-fd424f68f64c. User authorized creating/messaging/replacing all campaign chats and merging main.

## Scope / hard restrictions

- Player movement and historical collision shapes for era-existing blocks; vanilla block states. Exact math/order/casts and historical quirks.
- Exclude rendering/camera, non-player/vehicle physics, health/regen/food/hunger/saturation/exhaustion and damage/attack/combat production. Direct player movement response/velocity/knockback and predicates reading native state remain eligible.
- Version switching is undefined behavior, outside correctness guarantees. Fixed-profile parity only.
- ONLY builds and static checks. NO tests (including simulation), game clients, TAS, Gym/server or Docker launches/restarts. Disable ALL Gradle Test tasks explicitly; -x test alone may miss subprojects.
- Independent minimal deltas, thin shared mixins, no wholesale loops/classes duplication. No resolver refactor/heavy libraries without required approval.
- Normal source workers and their blind reviewers: vanilla sources only; prior source reports navigation only; no mod implementation, no Minecraft/MCPK wiki or wiki findings before full-pair freeze.
- Minecraft Wiki and MCPK Wiki are separate lanes, may corroborate exact source, must not run normal discovery workflow. Give each only its own wiki material. Use separate wiki-aware independent review, not a normal blind reviewer.
- Each accepted immutable finding may go to a separate implementation owner before full pair finishes. Requires exact source commit/path/fileSHA, source/artifact identities and finding-specific producer/consumer closure, plus independent blind acceptance of EXACT bytes.
- Full-pair complete requires all inventories/call graph/deps terminal and independent coverage audit; a worker stopping, populated catalog, individual acceptance, or successful build does not complete a pair. Keep discovery, implementation and runtime status separate.

## What is already built

Root baseline457b350fa5490b90b08de4f97033718c77efec1d is clean and built yesterday.
Includes1.8 cutoff<0.005, corrected16 pane/bar masks, physical sneakheight1.8, later1.65 crouch dimensions,1.15.2 open-shulker escape correction. Farmland was already baseline coverage. Health/food emulation removed. Generic dimension epoch refresh exists but switching is not a correctness target.
Yesterday build passed with all Test tasks disabled. Root JAR build/libs/LegacyParkourCompat+26.2-1.0.0.jar SHA25691E3A399832AFCD425307D5234CE77719DFD0BC473BE9CE533E8DA1CFDB0BA7B. No runtime tests.
Retrospective already exists workflows/retrospective-2026-10-07.md; do not restart a broad retrospective. Resetebe56a2 removed old cutoff/pane/standing-pose patches while narrow partial comparisons/catalog/builds were mistaken for coverage.
At yesterday checkpoint all26 pairs incomplete. Today additional source work/commits exist; use latest checkpoints/ref tips, not yesterday status as current evidence.

## Current code awaiting review/integration — prioritize this

1. TICK-01 sprint timeout order: code3c531180287b3cecff49e557083db73352b258f7 branchfeat/movement-sprint-timeout-2026-10-08. Existing600tick timer moved dispatcher LocalPlayer.aiStep TAIL→HEAD, hook description updated. Static review NOT yet accepted. Exact source1dbafbb7899c57ddf1718e421aa2c32e283fc7c7:workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-01-sprint-timeout.md SHA f7fb9e8aaf8685c65a2af1ce23c43f9d52e44393280c4047a5fd4d6cfc0788d5; blind304b6a58384a9a724d3ab9ad3f3bab5650bacdf5, workflows/coverage-review-2026-10-08/TICK-01.md.
2. Passenger current-yaw refresh F006: code8722bc476159b79f7e249f48b0e1076f209ced12 branchfeat/movement-passenger-yaw-2026-10-08, latesthandoff48003fe. Owned direct Git worktree C:/Users/Wolfi/.codex/worktrees/movement-passenger-yaw-2026-10-08. Static review pending. Snapshot064fc24e5e3d8b4dab4f12c2000dcb13d37514ca:1.17.1--1.18.2/findings/F-006-boat-passenger-yaw-refresh.md under campaign path, SHA3994115e3789a8280aa61228b2b092a007e57985cde72492f19731a8f150db95; blind4ff925e4e4186adb704d57d9b3471c559945436c (included9e4f81c), middle.md. Packet handler captures preexisting passenger at ejectPassengers after thread check; resets current player yaw if still on same boat. Render-only fields excluded. CRITICAL REVIEW: owner added EXACT V1_17_1 selected-profile guard and claimed resolver applies changes to later profiles; independently verify actual closest-applicable resolver direction and document uncertain/older profile coverage, not blindly accept this reasoning. Native lifecycle/initialmount preserved.
3. F0021.13 posefit: codee6b61bd7f1334b7e4fa49a9bd0a5929e833338a8, handoffa53cb6b503829bfa9b7351a87a0ad1d90f1c0f26. SEPARATE CLONE/objectstore D:/Javastuff/LegacyParkourCompat/build/codex-worktrees/movement-posefit branchfeat/movement-pose-fit-2026-10-08. FETCH this local clone into private review/integration ref to import objects; not yet in shared repo. Static review pending. Exact source448934e826fb41266dc79a313ef1187899d76382:workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md SHAbaa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e; blind10aa24e1114200397ed2d042a33f269113faf179. Null-query-entity/full-box historical fit and old-size retention versus modern deflated/entity-aware queries; independent native binding/profile/order review required.

Old static review handoff commit1f5fe40ec77f2003f7245cac6da91a996d88bd69 onfeat/movement-static-review-2026-10-08:workflows/orchestration-2026-10-08/handoffs/01a1171c-9325-76e2-9670-fb4498142cdd.md. Read before fresh reviewer continues.
Integration branchfeat/movement-campaign-integration-2026-10-08 at958b0916f08cba25e3be86d67d48a9d1e8651d1f. Only compact queue committed; NO new code merged/built today. Latest integration worktree vanished again, uncommitted queue edit/staging uncertain; recover from preserved ref, never assume attachment implies directory exists. New integration owner exclusively owns build slot.
Finish review of each patch and integrate/build accepted batches immediately, not after every report merges.

## Accepted source findings / implementation queue

Exact paths below rooted workflows/source-campaign-2026-10-07/<pair>/findings. Reviewreports workflows/coverage-review-2026-10-07/{early,middle,recent}.md. Verify latest immutable bytes and supersession events before using.

-1.14.4--1.15.2: three findings in4a0c35f2008d785867c00360a7b72726e3506435, acceptedearly960ce5644ceacd8e27bd3cb9145bd4eaf221379c:
 F-SOUL-SAND-SPEED.md SHAa43043bed85d233478d323621ed97ef7ac27e7fc9a286f439dc2cd5280604f7e (accepted-01 only; later68af793 snapshotpending)
 F-FRICTION-SAMPLE.md SHAca432d90d831032a8ca4574024a1442127705a197a6f329c321944f9dadb67c3
 F-ELYTRA-START.md SHAb6d5091e95e7cc0d35eb696dea6d06f720aaad91540ad88915ddc61dad576e59.
 Old implementation01a11b45-7f77-7381-a3d0-7f2899ee847b checkpoint requested; inspect final handoff for actual edits, don't repeat source admission/setup. Firstchangedrelease interval unknown. Existing code may cover parts; patch missing behavior only.
-1.15.2--1.16.5: F-S2-EDGE.md ce9369edd2bfe296e56a0a5253a47a5cd486dc3a SHA7382329a99db906b15a366a6ee2b6567aa50924eb7fb15e0b07d771d5aa02a3b, acceptedb27ba28337e6e7ef04d3005e2cae980c7e4ca40e. Oldimpl01a11752 checkpoint may conclude existing edge coverage; inspect before duplicate code. Same owner already verified sprintreset and waterdescent are covered at457b350 (binding/static only).
-1.18.2--1.19.2 F-002.md source1d5f18176eccc5103c08bd1807b2f2c32f2b3376 SHA4a4a23d071397f4cd3a1c82ef4e4ff59885df9b63c79b425c927c310bd1dbbb9, accepted0648b843fecf2358165a7c387cf348b02c66396b. Rising positiveY crouch edge restraint before collision; later adds requestedDelta.y<=0 guard. Implementation pending. A create_thread request titled "Implement rising crouch edge restraint" timed out; NO returned threadid and not found first50sidebar entries. Ops lookup unresolved; verify local metadata before duplicating or count as possibly active until resolved.
-1.20.1--1.20.2 F-1.20.2-PASSENGER-CROUCH-INPUT source4ee5bbce85b193f972766c324c262e375fed9bd6 SHA5e22fc1bf04bee71559848d15ce5eaacfc86294b5abb0549b6ea63a20140c3b5, accepted626fd4ffc3982169be5990949049ee0d2e3e1bb7. Oldimpl01a11af3 checkpoint3d4909ebebfe5b3e936bc591f85f2de711a6c1df feat/movement-passenger-crouch-input-2026-10-08. Sourceadmissiondone, existingpassengercrouchhooknotyetinspected, no newcode. That5e22fc SHA is FINDING file, not vanilla LocalPlayer hash; don't repeat prior false integrity alarm.
-1.19.3--1.19.4 F04fallflyingsprint already covered at457b350, oldimpl01a11755 precise code evidence in checkpointJSON; no new patch.
-New middle acceptances in4fe0e809579ea9e60e65966eff635c7f9380adf1: spectator-entry verticalvelocityreset snapshot3cf06002572685faba3eb28586b1986ff2ac8319 SHA730a8046d3b084ae3252e3d3dda9fee48f34916202869beea781d0481c24af84a review92b5ce48749bc5f6c06d671598952ea4be65bad2; and1.20.4--1.20.6 MC1204-1206-05 stepheightinput snapshot8797e40a04bbcb2de46fa4a689b181afb8a1a4ad SHAa6296dcbedc454962625693587baf4d4a3a950f4453518178d6799bca92a26d4. Eligibleboundedinput, collisionoutcomesnotestablished. Freshimplementationneededifmissing.
-Earlyreviewhandoffcf1b65bf1bc69a04505863f0f3f01a63bc9cf923 feat/coverage-review-early-resume includes accepted1.10.2--1.11.2 F02 piston requestedmovementcap andF05; exactsnapshotsinthatfile, route freshimplementation.
-Sleep-safety sourceacceptedcdc8db327b9d99fb6344c1f98e2f12f8774dc6a1 SHAd98ec64359ac2cc8857383a9f6ddffdcf3c3b003c8752f9b85bfb290f470b797, early37e7027f86c4ac5f23ebc73aa19f3c68508e8cb9 +eligibilityd7530cb8ad10be53c6d4a85a69048d35f9046bc9. Scope onlydirectdimension/position/sleepstate/velocitytransition, not general bed rules/mobAI/anger production. Needs implementation feasibility/scope judgment, don't broaden gameplay.

## Source review backlog

Use old reviewer checkpoint files/finalJSON for latest exact state.
-1.19.3--1.19.4 F03mounted sprintcarryover revisionrequired b83d43be...; owner got feedback and is correcting source snapshot. Bcanrestartafterdismount viaheldsprint/positive trigger timer; must narrow no-restart precondition.
-1.17.1--1.18.2 F001minorcollision sprintstop correcting erroneous Shapes.java checksum, new immutable supersession required before acceptance.
-1.21.3--1.21.4 F-S1-02 snapshot098afc2049c76c3697ae6f5b785beda4d80e8be1 SHAe753ccb47be9a69ff8977741d7863d637c182648a7574f3a87280d5cf1991fa1: substantive source review complete but exact acceptance record pending. OtherS1/S3 findings need exactsnapshot review.
-1.21.5--1.21.8 portalshape017be74131a8ca71db70d1d7593a52ce880977b6 SHA3af8ce3dfe99b5b60051362d3cc117143c45ffa8f5a39cb800860410afbe3cbb: bounded reviewdecisionrecord pending. Playervalidationcontext68e042815f4cfed5c2aa5f4644335237b6f414cd exactsource reviewnotstarted.
-1.21.1--1.21.3 contacttraverse correctedLocalPlayer isEffectiveAi override(true) restoresreachableclientcallback. Ownermustsnapshotcurrentbytesandreviewafteroldwronggate removed. Tinypositionwrite snapshot6aa873efa6f2fb7ffe3f545674859a9fff82273e SHAbc3bcb15fe51d8cf3917d788c069ada356c332f609bcc139b0703313ce5368c1 awaitingreview.
-Do not interpret unknown firstchangedpatch within acceptedmajorinterval as blocking all implementation; use evidencedendpoint/groupboundaries and explicitlydocumentuncertainty, don'tinventminorbehavior.

## Exact source publication, no competing preparation

Central readonly root D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07.
ready/<version>/ornithe-feather through1.14.4; ready/<version>/mojmap1.14.4..1.21.11; ready/<version>/unobfuscated26.x.
All30namespace trees and130417Java entries previously independentlyverified. Six revisedFeather publications feather-r1-2026-10-07 under revisions/derived-artifact-snapshots; originalmappedJARs unavailable, equivalenceunproven. Cite exactrevisedpublicationidentity, don'tclaimreproduction.
26.2sourceLocalPlayer.java SHA8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6; nativejar artifacts/26.2/client.jar SHA40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290. No mappedjar needed.
Root directlyverified ready/26.2/unobfuscated.ready.json exists afterPCrestart. Severalworkers wronglycheckedtheirlocalcheckout/otherworkerpaths andreportedmissing; usecanonicalpath. Narrowmanagedreadaccessifneeded, notdecompile/recoveryloops.
Relevantcitedfilehashes stillverify per finding/review; don'trehashwhole130kpublicationeverytime.
Source prep branchfeat/movement-campaign-sources@2f71b11.

## Fresh-worker scheduling / operational recovery

old-chats.json maps43oldids/roles/pairs. old-chat-checkpoints.json captures their lastmessages, including handoffs when filesystemwritesfailed. Finalsource checkpoints in eachownbranch pair run.md; sourcebranches mayhavecontinuation/audit/resume names! Use finalmessage+savedbranch-tip listing, not assumedconvention. Allsource pairs stillincompleteunlesslaterindependentauditprovesotherwise.
Do not give mixed orchestrator files to source workers. Extract only pair-own branch/commit/report/nextsourceactions +canonicalpublication and appropriateworkflow. Reviewers getsource-only evidence; implementation getsacceptedfinding+existingcode; wikiownersgetonlyassignedwiki+owncheckpoint.
Use fresh create_thread chats, notforks. Read exactrolehandoff first; ownmanagedcheckoutfrompreservedref or safeisolatedclone; avoid repeatedsetupadmission alreadyverified. Uncommittededits mayremaininoldpath; preserve/inspectbeforecloning.
Track durable role/id/ref/status/slots/tooloutcomes. Start replacement roles only within<=15totalactiveconservativecap includingcoordinator. Prefer reserveintegration+staticreview+implementationcapacity rather than26sourceownerssimultaneously; process remainingpairqueueinwaves. Retirecompletedfreshworkers andcreatefreshassignmentchatswithminimalrolecontext.
Onlyoneownerbuildslot andonesharedartifactwriter. No global scratch/source checkout writersoverlap.
Tool-host resets lose functions.store andcells. Persiststateinfiles. Unknown send/create outcome: readthread/localmetadata before retry. read_thread and wait_threads directIDs work evenwhen list_threads first50omitallcampaignchats. list_threads limitmax50. wait_threads max8 and timeout0compact; completedtargetmaydominatefirstwake.
Missingworktree vspermissions: explicitownedworkdir, Test-Path, gitworktreelist, narrowrequire_escalated access. NevereditGitconfigs/safe.directory/core.worktree/reset/delete/recreatehealthycheckout. Primarycheckoutmuststaycleanuntilauthorizedintegrationto main.
Localclonecodeobjects needfetch(privateintegrationref)/bundle beforecherrypick. Do notassumecommitaccessiblefromsharedrepo.
Do notsendrootbackmessagesunlesshumanexplicitauthorizationcovers it; rootreadsworkerresults. Newcoordinatorcanrouteworkerchatsunderuserauthorization.
Atendmergecompletedcode/docsinto main afterreview/build, verifycleanmain+JARhash, reportunfinishedsourcecoveragehonestly. Don'tstopatlaunch/reports; persistanddispatchimplementation asacceptedfindingsarrive.

## Wiki resume leads

Minecraft Wiki oldbranchfeat/minecraft-wiki-audit@2d20e330083ca7bb738e750ae329bbf67d618ac2. Fence besideEndPortalFrame: A1.8.9stone/materialinheritscube; B1.9.4 isCube(state)false dropsconnectionarm. Fencefilehashesverified, EndPortalFrame/Material hashes stillneedrecord; noimmutablefindingyet. Swimmingpitch commitsb20730f/b74b447, Featherfinding4c1a8a01. Continueownwikicatalog, independentwiki-awarereview, no normalworkflow.
MCPK oldbranchfeat/mcpk-wiki-audit, finalhandoffpendingattransition; checkpointJSONandlatestrefgiveexactresume. Recentwaterentryguard/bigdripleaf/slipperiness snapshots; sourcecorroborateandseparatefirstentryvscontinuation. No wiki-lanecoveragecompleteclaim.

## Historical context checked

## Final checkpoint updates

- Fourth new code patch: 1.14 Elytra start gate commit bedf96068bde90d295b6446aa4501b91ebf8f319 on feat/movement-1-14-deltas-2026-10-08. One known correction remains before review: replace rejection y >= 0.0 with exact !(y < 0.0). Existing Soul Sand and friction changes appear present; independent static review still needed.
- Early accepted piston snapshot: 5adb40184db92d89bd2ab9ed9e3c6f9ea4774f83, F-02-piston-movement-cap SHA a8acac9889d38713e6e116daa2734eb214fdacac87525c10f157ce9c813f904f, review8451c30c611b854f7942f40971440b8fec14c68a. Ready for implementation if missing.
- MCPK checkpoint ae651e6, snapshots f4d5dcf (Big Dripleaf),3de1093 (swimming entry),bb05061 (slipperiness examples); independent review pending.
- Old 1.15 owner verified A-side F-S2-EDGE existing coverage, but exact1.16.5 probe applicability remains open. Do not mistake its no-code result for all-profile closure.
- Old queue report repeats a false passenger hash mismatch. Disregard that metadata claim: 5e22fc is the FINDING hash, bb5cbf is the VANILLA LocalPlayer.java hash. They are different artifacts and should differ.
- Root handoff commit/merge needs narrow Git metadata write permission because .git/worktrees metadata lives outside this worktree. Plain git merge main failed ORIG_HEAD.lock permission denied; this is not evidence of Git corruption. Use narrowly approved access; do not repair Git config.
- Some old workers could not write their handoff because paths disappeared or write approval was rejected. Their full final handoffs are preserved in old-chat-checkpoints.json; no reason to restart them just to write a file.

Prior user requests and chat summaries were reread before this handoff. Important corrections: keepcurrentcode; wikiusepermittedonlyseparatelanes; latesthotfixpernamedcontentdrop; health/foodout; no tests/runtime; versionswitchundefined; implementacceptedfindingswhilepairactive; mergeultimatelymain; new15cap/freshcontexts supersedeoldmassparallelreactivation.

