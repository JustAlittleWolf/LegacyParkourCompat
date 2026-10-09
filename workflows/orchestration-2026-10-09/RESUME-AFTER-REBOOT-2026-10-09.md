# Campaign restart checkpoint — 2026-10-09

The user explicitly requested all workers halt before restarting the device. Do not resume until the user authorizes it after reboot.

Coordinator chat: 01a12180-70f4-7300-90c4-a97f4baddf0d. Coordinator worktree: D:/Javastuff/LegacyParkourCompat/.task-worktrees/campaign-coordinator-2026-10-09. Primary checkout remains main; last observed HEAD309b330d546da8e0e25fa86158e993454e58f53f. Preserve all worktrees and uncommitted worker files.

The authoritative companion restart-state-final.json contains all 33 known worker chat IDs, final cursors, task roles and paused handoffs. All 33 tracked chats are idle; workers confirmed their own processes and subagents stopped. The earlier restart-state.json is the intermediate pause-request snapshot. worktrees-before-reboot.txt records Git worktree paths, branches and HEADs.

After reboot: inspect every saved chat nonblocking, consume paused handoffs and inspect Git/worktree status before resuming. Resume related assignments in their original workers; create fresh workers for unrelated work. Target about15 active campaign workers, a soft target excluding coordinator. Wait across the whole active roster in concurrent batches <=8; first completion/attention wins, then nonblocking remaining-roster sweep, route results/refill before waiting again. Do not create duplicate assignments.

One shared exact source cache is D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready. Only preparation owner writes; source workers read-only. Pending26.1/26.1.1 prep is required before F4 resolverboundary; check partial/prep state before resuming.1.13.2 original mappedJAR identity remains unresolved; sourcefiles individually verified, no bytecodeclaim.

Exclusive integration owner controls primary/main/index/build. F005/F3/F012 have reached main; verify exact final buildrecords from owner. F008 portable review, F007 review, Y256 implementation, early sprintboundary and F21 source review may have paused mid-task. Preserve source-only/mod/wiki/MCPK isolation. No tests/runtime/clients/TAS/servers/Docker; authorized builds require all Gradle Test tasks disabled plus-x test. No push or cleanup.

Clean currentflow r2 review cc8d9585737e4a4d0e5f1af3a1837c0b24bd8544 is REQUEST CHANGES: qualify LivingEntity.mobTick cutoff below0.003 beforetravel. This result has not yet been routed to originalMCPKowner at pause. Preserve originalmemo/rejections, correct separate snapshot then relatedcleanreview. F009 review f539b65862249af400c4ff15553323f864ddd3cf rejected vanilla /teleport reachability; originalsourceowner received correction and is preserving revision. Eyeheightreview found correctedwithdrawal only in run, missingseparateimmutablememo; sourceowner received thatrequest.

