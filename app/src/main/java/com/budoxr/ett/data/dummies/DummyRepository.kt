package com.budoxr.ett.data.dummies

import com.budoxr.ett.data.adapters.TimerTrackingQueryAdapter
import com.budoxr.ett.data.adapters.TimersWithActivityAdapter
import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.squareup.moshi.Moshi

class DummyRepository(
    private val timersWithActivityAdapter: TimersWithActivityAdapter,
    private val timerTrackingQueryAdapter: TimerTrackingQueryAdapter,
) {
    private val timersWithActivityJson = """
        [{"timerTracking":{"timer_tracking_id":435,"start_time":"2026-06-22 06:55:03","elapsed_time":0,"visible":true,"done":false,"activity_id":11},"activity":{"activity_id":11,"name":"EASY TIME TRACKING","color":""}}]
    """.trimIndent()
    fun allTimersWithActivity(): List<TimersWithActivity>  {
        val adapter = timersWithActivityAdapter.adapter
        val timers = adapter.fromJson(timersWithActivityJson)
        return timers ?: emptyList()
    }

    private val timerTrackingQueryJson = """
    [
    	{
    		"timerTracking": {
    			"timer_tracking_id": 1,
    			"start_time": "2026-05-27 10:35:04",
    			"end_time": "2026-05-27 11:17:01",
    			"elapsed_time": 2517,
    			"visible": false,
    			"done": true,
    			"activity_id": 1
    		},
    		"nameActivity": "AARON MEETING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 2,
    			"start_time": "2026-05-28 16:47:43",
    			"end_time": "2026-05-28 17:43:55",
    			"elapsed_time": 3372,
    			"visible": false,
    			"done": true,
    			"activity_id": 1
    		},
    		"nameActivity": "AARON MEETING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 3,
    			"start_time": "2026-05-29 08:24:34",
    			"end_time": "2026-05-29 08:25:41",
    			"elapsed_time": 67,
    			"visible": false,
    			"done": true,
    			"activity_id": 1
    		},
    		"nameActivity": "AARON MEETING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 4,
    			"start_time": "2026-06-05 11:15:32",
    			"end_time": "2026-06-05 11:24:57",
    			"elapsed_time": 565,
    			"visible": false,
    			"done": true,
    			"activity_id": 1
    		},
    		"nameActivity": "AARON MEETING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 5,
    			"start_time": "2026-06-10 10:32:04",
    			"end_time": "2026-06-10 13:03:54",
    			"elapsed_time": 9110,
    			"visible": false,
    			"done": true,
    			"activity_id": 1
    		},
    		"nameActivity": "AARON MEETING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 6,
    			"start_time": "2026-06-17 11:16:03",
    			"end_time": "2026-06-17 12:04:32",
    			"elapsed_time": 2909,
    			"visible": false,
    			"done": true,
    			"activity_id": 1
    		},
    		"nameActivity": "AARON MEETING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 7,
    			"start_time": "2026-05-22 10:36:57",
    			"end_time": "2026-05-22 11:04:37",
    			"elapsed_time": 1660,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 8,
    			"start_time": "2026-05-23 14:42:25",
    			"end_time": "2026-05-23 15:12:57",
    			"elapsed_time": 1832,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 9,
    			"start_time": "2026-05-24 06:31:18",
    			"end_time": "2026-05-24 07:17:19",
    			"elapsed_time": 2761,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 10,
    			"start_time": "2026-05-27 16:10:10",
    			"end_time": "2026-05-27 16:41:49",
    			"elapsed_time": 1899,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 11,
    			"start_time": "2026-06-03 21:20:26",
    			"end_time": "2026-06-03 23:11:22",
    			"elapsed_time": 6656,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 12,
    			"start_time": "2026-06-04 09:02:32",
    			"end_time": "2026-06-04 09:33:59",
    			"elapsed_time": 1887,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 13,
    			"start_time": "2026-06-04 09:42:37",
    			"end_time": "2026-06-04 11:08:47",
    			"elapsed_time": 5170,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 14,
    			"start_time": "2026-06-04 11:13:14",
    			"end_time": "2026-06-04 11:59:01",
    			"elapsed_time": 2747,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 15,
    			"start_time": "2026-06-05 16:58:16",
    			"end_time": "2026-06-05 18:36:31",
    			"elapsed_time": 5895,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 16,
    			"start_time": "2026-06-05 20:02:01",
    			"end_time": "2026-06-05 20:22:40",
    			"elapsed_time": 1239,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 17,
    			"start_time": "2026-06-05 20:23:14",
    			"end_time": "2026-06-05 21:01:52",
    			"elapsed_time": 2318,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 18,
    			"start_time": "2026-06-09 10:48:58",
    			"end_time": "2026-06-09 12:02:33",
    			"elapsed_time": 4415,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 19,
    			"start_time": "2026-06-09 21:07:40",
    			"end_time": "2026-06-09 22:11:33",
    			"elapsed_time": 3833,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 20,
    			"start_time": "2026-06-15 18:41:04",
    			"end_time": "2026-06-15 19:21:34",
    			"elapsed_time": 2430,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 21,
    			"start_time": "2026-06-16 13:58:06",
    			"end_time": "2026-06-16 14:27:38",
    			"elapsed_time": 1772,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 22,
    			"start_time": "2026-06-18 14:17:01",
    			"end_time": "2026-06-18 14:31:02",
    			"elapsed_time": 841,
    			"visible": false,
    			"done": true,
    			"activity_id": 2
    		},
    		"nameActivity": "ADMIN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 23,
    			"start_time": "2026-05-23 23:43:46",
    			"end_time": "2026-05-23 23:49:28",
    			"elapsed_time": 342,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 24,
    			"start_time": "2026-05-28 21:23:31",
    			"end_time": "2026-05-28 22:18:47",
    			"elapsed_time": 3316,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 25,
    			"start_time": "2026-06-03 06:36:59",
    			"end_time": "2026-06-03 06:44:00",
    			"elapsed_time": 421,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 26,
    			"start_time": "2026-06-04 18:43:26",
    			"end_time": "2026-06-04 18:46:30",
    			"elapsed_time": 184,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 27,
    			"start_time": "2026-06-06 10:10:52",
    			"end_time": "2026-06-06 10:58:49",
    			"elapsed_time": 2877,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 28,
    			"start_time": "2026-06-09 10:45:24",
    			"end_time": "2026-06-09 10:48:55",
    			"elapsed_time": 211,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 29,
    			"start_time": "2026-06-09 12:04:08",
    			"end_time": "2026-06-09 14:12:38",
    			"elapsed_time": 7710,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 30,
    			"start_time": "2026-06-10 23:47:50",
    			"end_time": "2026-06-11 01:08:29",
    			"elapsed_time": 4839,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 31,
    			"start_time": "2026-06-11 20:36:49",
    			"end_time": "2026-06-11 21:09:36",
    			"elapsed_time": 1967,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 32,
    			"start_time": "2026-06-12 12:43:44",
    			"end_time": "2026-06-12 13:09:45",
    			"elapsed_time": 1561,
    			"visible": false,
    			"done": true,
    			"activity_id": 3
    		},
    		"nameActivity": "BROWSING WEB"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 33,
    			"start_time": "2026-06-03 17:46:18",
    			"end_time": "2026-06-03 19:47:40",
    			"elapsed_time": 7282,
    			"visible": false,
    			"done": true,
    			"activity_id": 4
    		},
    		"nameActivity": "BUDOXR"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 34,
    			"start_time": "2026-06-06 07:52:22",
    			"end_time": "2026-06-06 08:18:57",
    			"elapsed_time": 1595,
    			"visible": false,
    			"done": true,
    			"activity_id": 4
    		},
    		"nameActivity": "BUDOXR"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 35,
    			"start_time": "2026-06-08 11:48:36",
    			"end_time": "2026-06-08 12:01:50",
    			"elapsed_time": 794,
    			"visible": false,
    			"done": true,
    			"activity_id": 4
    		},
    		"nameActivity": "BUDOXR"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 36,
    			"start_time": "2026-06-12 11:12:12",
    			"end_time": "2026-06-12 11:38:10",
    			"elapsed_time": 1558,
    			"visible": false,
    			"done": true,
    			"activity_id": 4
    		},
    		"nameActivity": "BUDOXR"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 37,
    			"start_time": "2026-05-23 09:31:24",
    			"end_time": "2026-05-23 11:50:39",
    			"elapsed_time": 8355,
    			"visible": false,
    			"done": true,
    			"activity_id": 5
    		},
    		"nameActivity": "CLEAN"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 38,
    			"start_time": "2026-06-16 18:09:03",
    			"end_time": "2026-06-16 19:19:06",
    			"elapsed_time": 4203,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 39,
    			"start_time": "2026-06-17 06:33:43",
    			"end_time": "2026-06-17 07:51:49",
    			"elapsed_time": 4686,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 40,
    			"start_time": "2026-06-17 09:49:29",
    			"end_time": "2026-06-17 11:06:31",
    			"elapsed_time": 4622,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 41,
    			"start_time": "2026-06-17 12:32:59",
    			"end_time": "2026-06-17 14:03:24",
    			"elapsed_time": 5425,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 42,
    			"start_time": "2026-06-17 21:03:59",
    			"end_time": "2026-06-17 23:23:20",
    			"elapsed_time": 8361,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 43,
    			"start_time": "2026-06-18 06:18:02",
    			"end_time": "2026-06-18 07:06:11",
    			"elapsed_time": 2889,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 44,
    			"start_time": "2026-06-18 08:28:23",
    			"end_time": "2026-06-18 08:44:17",
    			"elapsed_time": 954,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 45,
    			"start_time": "2026-06-18 09:40:08",
    			"end_time": "2026-06-18 10:39:38",
    			"elapsed_time": 3570,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 46,
    			"start_time": "2026-06-18 12:30:16",
    			"end_time": "2026-06-18 13:57:55",
    			"elapsed_time": 5259,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 47,
    			"start_time": "2026-06-18 16:43:27",
    			"end_time": "2026-06-18 17:00:04",
    			"elapsed_time": 997,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 48,
    			"start_time": "2026-06-18 17:00:20",
    			"end_time": "2026-06-18 17:32:29",
    			"elapsed_time": 1929,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 49,
    			"start_time": "2026-06-19 09:22:30",
    			"end_time": "2026-06-19 09:59:50",
    			"elapsed_time": 2240,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 50,
    			"start_time": "2026-06-19 11:37:03",
    			"end_time": "2026-06-19 12:49:44",
    			"elapsed_time": 4361,
    			"visible": false,
    			"done": true,
    			"activity_id": 6
    		},
    		"nameActivity": "CONDOMINIO"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 51,
    			"start_time": "2026-05-22 09:32:00",
    			"end_time": "2026-05-22 10:36:37",
    			"elapsed_time": 3877,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 52,
    			"start_time": "2026-05-22 13:23:02",
    			"end_time": "2026-05-22 13:37:51",
    			"elapsed_time": 889,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 53,
    			"start_time": "2026-05-22 22:22:26",
    			"end_time": "2026-05-22 23:07:30",
    			"elapsed_time": 2704,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 54,
    			"start_time": "2026-05-23 08:12:24",
    			"end_time": "2026-05-23 09:31:26",
    			"elapsed_time": 4742,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 55,
    			"start_time": "2026-05-23 13:58:36",
    			"end_time": "2026-05-23 14:40:44",
    			"elapsed_time": 2528,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 56,
    			"start_time": "2026-05-23 21:32:41",
    			"end_time": "2026-05-23 22:09:02",
    			"elapsed_time": 2181,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 57,
    			"start_time": "2026-05-24 10:01:52",
    			"end_time": "2026-05-24 10:48:30",
    			"elapsed_time": 2798,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 58,
    			"start_time": "2026-05-25 13:47:18",
    			"end_time": "2026-05-25 14:21:37",
    			"elapsed_time": 2059,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 59,
    			"start_time": "2026-05-25 20:03:01",
    			"end_time": "2026-05-25 20:40:42",
    			"elapsed_time": 2261,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 60,
    			"start_time": "2026-05-26 11:50:42",
    			"end_time": "2026-05-26 12:38:04",
    			"elapsed_time": 2842,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 61,
    			"start_time": "2026-05-26 18:39:04",
    			"end_time": "2026-05-26 19:00:07",
    			"elapsed_time": 1263,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 62,
    			"start_time": "2026-05-27 09:24:21",
    			"end_time": "2026-05-27 10:04:21",
    			"elapsed_time": 2400,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 63,
    			"start_time": "2026-05-27 16:41:51",
    			"end_time": "2026-05-27 18:36:19",
    			"elapsed_time": 6868,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 64,
    			"start_time": "2026-05-28 09:10:14",
    			"end_time": "2026-05-28 10:01:11",
    			"elapsed_time": 3057,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 65,
    			"start_time": "2026-05-29 09:09:35",
    			"end_time": "2026-05-29 09:25:30",
    			"elapsed_time": 955,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 66,
    			"start_time": "2026-05-29 21:56:36",
    			"end_time": "2026-05-29 22:03:27",
    			"elapsed_time": 411,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 67,
    			"start_time": "2026-05-29 23:01:10",
    			"end_time": "2026-05-29 23:54:07",
    			"elapsed_time": 3177,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 68,
    			"start_time": "2026-05-30 09:17:49",
    			"end_time": "2026-05-30 10:12:34",
    			"elapsed_time": 3285,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 69,
    			"start_time": "2026-05-30 18:04:16",
    			"end_time": "2026-05-30 19:09:30",
    			"elapsed_time": 3914,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 70,
    			"start_time": "2026-05-31 09:22:41",
    			"end_time": "2026-05-31 09:34:57",
    			"elapsed_time": 736,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 71,
    			"start_time": "2026-05-31 14:20:38",
    			"end_time": "2026-05-31 14:47:37",
    			"elapsed_time": 1619,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 72,
    			"start_time": "2026-05-31 23:00:00",
    			"end_time": "2026-06-01 00:03:01",
    			"elapsed_time": 3781,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 73,
    			"start_time": "2026-06-01 10:07:52",
    			"end_time": "2026-06-01 10:49:54",
    			"elapsed_time": 2522,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 74,
    			"start_time": "2026-06-01 15:14:00",
    			"end_time": "2026-06-01 17:08:35",
    			"elapsed_time": 6875,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 75,
    			"start_time": "2026-06-02 08:51:01",
    			"end_time": "2026-06-02 09:59:28",
    			"elapsed_time": 4107,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 76,
    			"start_time": "2026-06-02 16:14:57",
    			"end_time": "2026-06-02 17:10:24",
    			"elapsed_time": 3327,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 77,
    			"start_time": "2026-06-02 23:07:23",
    			"end_time": "2026-06-02 23:20:39",
    			"elapsed_time": 796,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 78,
    			"start_time": "2026-06-03 09:52:50",
    			"end_time": "2026-06-03 09:59:35",
    			"elapsed_time": 405,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 79,
    			"start_time": "2026-06-03 15:24:56",
    			"end_time": "2026-06-03 15:55:11",
    			"elapsed_time": 1815,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 80,
    			"start_time": "2026-06-03 23:11:26",
    			"end_time": "2026-06-03 23:20:05",
    			"elapsed_time": 519,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 83,
    			"start_time": "2026-06-03 23:56:31",
    			"end_time": "2026-06-04 00:48:46",
    			"elapsed_time": 3135,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 81,
    			"start_time": "2026-06-04 08:58:05",
    			"end_time": "2026-06-04 09:42:32",
    			"elapsed_time": 2667,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 82,
    			"start_time": "2026-06-04 13:44:54",
    			"end_time": "2026-06-04 14:44:30",
    			"elapsed_time": 3576,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 84,
    			"start_time": "2026-06-05 08:48:50",
    			"end_time": "2026-06-05 09:56:04",
    			"elapsed_time": 4034,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 85,
    			"start_time": "2026-06-05 13:01:39",
    			"end_time": "2026-06-05 13:51:16",
    			"elapsed_time": 2977,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 86,
    			"start_time": "2026-06-07 10:15:35",
    			"end_time": "2026-06-07 10:30:53",
    			"elapsed_time": 918,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 87,
    			"start_time": "2026-06-07 16:58:59",
    			"end_time": "2026-06-07 17:16:02",
    			"elapsed_time": 1023,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 88,
    			"start_time": "2026-06-08 10:04:50",
    			"end_time": "2026-06-08 11:07:09",
    			"elapsed_time": 3739,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 89,
    			"start_time": "2026-06-09 08:30:55",
    			"end_time": "2026-06-09 09:13:32",
    			"elapsed_time": 2557,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 90,
    			"start_time": "2026-06-09 14:12:41",
    			"end_time": "2026-06-09 15:32:11",
    			"elapsed_time": 4770,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 91,
    			"start_time": "2026-06-09 22:11:40",
    			"end_time": "2026-06-09 23:31:07",
    			"elapsed_time": 4767,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 92,
    			"start_time": "2026-06-10 09:03:16",
    			"end_time": "2026-06-10 10:11:44",
    			"elapsed_time": 4108,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 93,
    			"start_time": "2026-06-10 13:04:04",
    			"end_time": "2026-06-10 13:38:17",
    			"elapsed_time": 2053,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 94,
    			"start_time": "2026-06-10 22:11:58",
    			"end_time": "2026-06-10 23:05:14",
    			"elapsed_time": 3196,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 95,
    			"start_time": "2026-06-11 09:53:18",
    			"end_time": "2026-06-11 10:46:35",
    			"elapsed_time": 3197,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 96,
    			"start_time": "2026-06-11 12:28:04",
    			"end_time": "2026-06-11 12:54:02",
    			"elapsed_time": 1558,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 97,
    			"start_time": "2026-06-11 14:24:04",
    			"end_time": "2026-06-11 15:10:38",
    			"elapsed_time": 2794,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 98,
    			"start_time": "2026-06-11 22:52:30",
    			"end_time": "2026-06-11 23:39:02",
    			"elapsed_time": 2792,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 99,
    			"start_time": "2026-06-12 09:53:05",
    			"end_time": "2026-06-12 11:07:59",
    			"elapsed_time": 4494,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 100,
    			"start_time": "2026-06-12 14:34:38",
    			"end_time": "2026-06-12 16:17:11",
    			"elapsed_time": 6153,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 101,
    			"start_time": "2026-06-15 10:32:12",
    			"end_time": "2026-06-15 11:07:09",
    			"elapsed_time": 2097,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 102,
    			"start_time": "2026-06-16 09:40:11",
    			"end_time": "2026-06-16 10:23:02",
    			"elapsed_time": 2571,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 103,
    			"start_time": "2026-06-16 23:09:23",
    			"end_time": "2026-06-16 23:37:49",
    			"elapsed_time": 1706,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 104,
    			"start_time": "2026-06-17 15:03:42",
    			"end_time": "2026-06-17 16:03:05",
    			"elapsed_time": 3563,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 105,
    			"start_time": "2026-06-18 08:44:20",
    			"end_time": "2026-06-18 09:40:06",
    			"elapsed_time": 3346,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 106,
    			"start_time": "2026-06-18 13:58:48",
    			"end_time": "2026-06-18 14:08:12",
    			"elapsed_time": 564,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 107,
    			"start_time": "2026-06-18 14:31:04",
    			"end_time": "2026-06-18 15:14:34",
    			"elapsed_time": 2610,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 108,
    			"start_time": "2026-06-18 23:24:36",
    			"end_time": "2026-06-18 23:51:08",
    			"elapsed_time": 1592,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 109,
    			"start_time": "2026-06-19 08:54:53",
    			"end_time": "2026-06-19 09:22:13",
    			"elapsed_time": 1640,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 110,
    			"start_time": "2026-06-19 12:52:09",
    			"end_time": "2026-06-19 13:55:54",
    			"elapsed_time": 3825,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 111,
    			"start_time": "2026-06-19 22:12:51",
    			"end_time": "2026-06-19 23:21:05",
    			"elapsed_time": 4094,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 112,
    			"start_time": "2026-06-20 11:19:50",
    			"end_time": "2026-06-20 12:39:39",
    			"elapsed_time": 4789,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 113,
    			"start_time": "2026-06-20 13:46:45",
    			"end_time": "2026-06-20 14:30:03",
    			"elapsed_time": 2598,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 428,
    			"start_time": "2026-06-21 12:00:43",
    			"end_time": "2026-06-21 12:11:45",
    			"elapsed_time": 662,
    			"visible": false,
    			"done": true,
    			"activity_id": 7
    		},
    		"nameActivity": "COOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 114,
    			"start_time": "2026-06-06 12:29:16",
    			"end_time": "2026-06-06 18:00:37",
    			"elapsed_time": 19881,
    			"visible": false,
    			"done": true,
    			"activity_id": 8
    		},
    		"nameActivity": "DANCE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 115,
    			"start_time": "2026-06-09 16:30:03",
    			"end_time": "2026-06-09 17:45:34",
    			"elapsed_time": 4531,
    			"visible": false,
    			"done": true,
    			"activity_id": 8
    		},
    		"nameActivity": "DANCE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 116,
    			"start_time": "2026-06-12 16:34:07",
    			"end_time": "2026-06-12 17:58:11",
    			"elapsed_time": 5044,
    			"visible": false,
    			"done": true,
    			"activity_id": 8
    		},
    		"nameActivity": "DANCE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 117,
    			"start_time": "2026-06-16 16:38:53",
    			"end_time": "2026-06-16 17:42:46",
    			"elapsed_time": 3833,
    			"visible": false,
    			"done": true,
    			"activity_id": 8
    		},
    		"nameActivity": "DANCE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 118,
    			"start_time": "2026-06-06 18:42:57",
    			"end_time": "2026-06-07 01:00:03",
    			"elapsed_time": 22626,
    			"visible": false,
    			"done": true,
    			"activity_id": 9
    		},
    		"nameActivity": "DINNING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 119,
    			"start_time": "2026-05-22 11:05:05",
    			"end_time": "2026-05-22 11:13:18",
    			"elapsed_time": 493,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 120,
    			"start_time": "2026-05-22 13:37:53",
    			"end_time": "2026-05-22 15:03:46",
    			"elapsed_time": 5153,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 121,
    			"start_time": "2026-05-22 15:27:10",
    			"end_time": "2026-05-22 22:22:21",
    			"elapsed_time": 24911,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 122,
    			"start_time": "2026-05-22 23:38:10",
    			"end_time": "2026-05-22 23:52:40",
    			"elapsed_time": 870,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 123,
    			"start_time": "2026-05-30 08:36:18",
    			"end_time": "2026-05-30 09:47:48",
    			"elapsed_time": 4290,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 124,
    			"start_time": "2026-05-30 10:12:36",
    			"end_time": "2026-05-30 11:50:41",
    			"elapsed_time": 5885,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 125,
    			"start_time": "2026-05-31 07:03:14",
    			"end_time": "2026-05-31 07:22:47",
    			"elapsed_time": 1173,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 126,
    			"start_time": "2026-05-31 07:23:28",
    			"end_time": "2026-05-31 09:22:38",
    			"elapsed_time": 7150,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 127,
    			"start_time": "2026-05-31 16:05:45",
    			"end_time": "2026-05-31 17:28:02",
    			"elapsed_time": 4937,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 128,
    			"start_time": "2026-05-31 20:34:25",
    			"end_time": "2026-05-31 22:59:48",
    			"elapsed_time": 8723,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 129,
    			"start_time": "2026-06-01 07:42:16",
    			"end_time": "2026-06-01 08:25:31",
    			"elapsed_time": 2595,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 130,
    			"start_time": "2026-06-01 09:05:33",
    			"end_time": "2026-06-01 10:07:49",
    			"elapsed_time": 3736,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 131,
    			"start_time": "2026-06-01 10:49:56",
    			"end_time": "2026-06-01 12:14:09",
    			"elapsed_time": 5053,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 132,
    			"start_time": "2026-06-01 12:26:24",
    			"end_time": "2026-06-01 13:17:49",
    			"elapsed_time": 3085,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 133,
    			"start_time": "2026-06-01 17:10:29",
    			"end_time": "2026-06-01 19:54:58",
    			"elapsed_time": 9869,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 134,
    			"start_time": "2026-06-01 21:56:16",
    			"end_time": "2026-06-01 23:39:17",
    			"elapsed_time": 6181,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 135,
    			"start_time": "2026-06-02 08:50:59",
    			"end_time": "2026-06-02 11:08:46",
    			"elapsed_time": 8267,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 136,
    			"start_time": "2026-06-02 12:09:05",
    			"end_time": "2026-06-02 13:06:41",
    			"elapsed_time": 3456,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 137,
    			"start_time": "2026-06-02 14:04:12",
    			"end_time": "2026-06-02 16:14:55",
    			"elapsed_time": 7843,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 138,
    			"start_time": "2026-06-02 19:25:59",
    			"end_time": "2026-06-02 23:07:22",
    			"elapsed_time": 13283,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 139,
    			"start_time": "2026-06-02 23:38:37",
    			"end_time": "2026-06-03 00:09:00",
    			"elapsed_time": 1823,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 140,
    			"start_time": "2026-06-03 06:57:20",
    			"end_time": "2026-06-03 09:52:53",
    			"elapsed_time": 10533,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 141,
    			"start_time": "2026-06-03 11:00:16",
    			"elapsed_time": -3600,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 142,
    			"start_time": "2026-06-04 18:47:36",
    			"end_time": "2026-06-04 23:48:12",
    			"elapsed_time": 18036,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 143,
    			"start_time": "2026-06-10 20:27:00",
    			"end_time": "2026-06-10 22:35:46",
    			"elapsed_time": 7726,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 144,
    			"start_time": "2026-06-10 23:05:11",
    			"end_time": "2026-06-10 23:47:46",
    			"elapsed_time": 2555,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 145,
    			"start_time": "2026-06-11 12:54:13",
    			"end_time": "2026-06-11 14:24:05",
    			"elapsed_time": 5392,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 146,
    			"start_time": "2026-06-11 18:59:16",
    			"end_time": "2026-06-11 20:35:17",
    			"elapsed_time": 5761,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 147,
    			"start_time": "2026-06-12 07:51:19",
    			"end_time": "2026-06-12 08:39:01",
    			"elapsed_time": 2862,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 148,
    			"start_time": "2026-06-12 11:38:14",
    			"end_time": "2026-06-12 12:26:16",
    			"elapsed_time": 2882,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 149,
    			"start_time": "2026-06-15 15:42:34",
    			"end_time": "2026-06-15 17:10:35",
    			"elapsed_time": 5281,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 150,
    			"start_time": "2026-06-15 17:15:58",
    			"end_time": "2026-06-15 17:32:13",
    			"elapsed_time": 975,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 151,
    			"start_time": "2026-06-16 07:22:57",
    			"end_time": "2026-06-16 09:40:17",
    			"elapsed_time": 8240,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 152,
    			"start_time": "2026-06-16 09:58:08",
    			"end_time": "2026-06-16 13:58:00",
    			"elapsed_time": 14392,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 153,
    			"start_time": "2026-06-20 06:31:28",
    			"end_time": "2026-06-20 08:14:36",
    			"elapsed_time": 6188,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 154,
    			"start_time": "2026-06-20 21:30:23",
    			"end_time": "2026-06-20 22:00:26",
    			"elapsed_time": 1803,
    			"visible": false,
    			"done": true,
    			"activity_id": 10
    		},
    		"nameActivity": "DRIBBLI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 155,
    			"start_time": "2026-05-22 02:55:57",
    			"end_time": "2026-05-22 09:04:56",
    			"elapsed_time": 22139,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 156,
    			"start_time": "2026-05-22 09:31:55",
    			"end_time": "2026-05-22 10:04:22",
    			"elapsed_time": 1947,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 157,
    			"start_time": "2026-05-23 06:37:23",
    			"end_time": "2026-05-23 08:10:19",
    			"elapsed_time": 5576,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 158,
    			"start_time": "2026-05-23 15:41:32",
    			"end_time": "2026-05-23 15:45:13",
    			"elapsed_time": 221,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 159,
    			"start_time": "2026-05-23 22:13:29",
    			"end_time": "2026-05-23 22:14:03",
    			"elapsed_time": 34,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 160,
    			"start_time": "2026-05-24 07:18:50",
    			"end_time": "2026-05-24 10:01:49",
    			"elapsed_time": 9779,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 161,
    			"start_time": "2026-05-24 13:29:18",
    			"end_time": "2026-05-24 13:51:45",
    			"elapsed_time": 1347,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 162,
    			"start_time": "2026-05-24 15:44:07",
    			"end_time": "2026-05-24 15:55:13",
    			"elapsed_time": 666,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 163,
    			"start_time": "2026-05-24 16:23:06",
    			"end_time": "2026-05-24 16:55:12",
    			"elapsed_time": 1926,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 164,
    			"start_time": "2026-05-24 20:19:41",
    			"end_time": "2026-05-24 21:29:14",
    			"elapsed_time": 4173,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 165,
    			"start_time": "2026-05-25 11:11:04",
    			"end_time": "2026-05-25 13:47:16",
    			"elapsed_time": 9372,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 166,
    			"start_time": "2026-05-25 14:55:21",
    			"end_time": "2026-05-25 15:59:28",
    			"elapsed_time": 3847,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 167,
    			"start_time": "2026-05-29 07:36:51",
    			"end_time": "2026-05-29 08:20:36",
    			"elapsed_time": 2625,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 168,
    			"start_time": "2026-06-03 15:02:20",
    			"end_time": "2026-06-03 15:24:54",
    			"elapsed_time": 1354,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 169,
    			"start_time": "2026-06-03 16:31:00",
    			"end_time": "2026-06-03 17:46:10",
    			"elapsed_time": 4510,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 170,
    			"start_time": "2026-06-03 23:20:12",
    			"end_time": "2026-06-04 00:46:17",
    			"elapsed_time": 5165,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 171,
    			"start_time": "2026-06-04 06:47:55",
    			"end_time": "2026-06-04 08:12:48",
    			"elapsed_time": 5093,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 172,
    			"start_time": "2026-06-04 11:59:08",
    			"end_time": "2026-06-04 13:44:49",
    			"elapsed_time": 6341,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 173,
    			"start_time": "2026-06-04 15:51:05",
    			"end_time": "2026-06-04 17:46:06",
    			"elapsed_time": 6901,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 174,
    			"start_time": "2026-06-04 17:49:02",
    			"end_time": "2026-06-04 18:12:09",
    			"elapsed_time": 1387,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 175,
    			"start_time": "2026-06-05 07:22:53",
    			"end_time": "2026-06-05 09:46:01",
    			"elapsed_time": 8588,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 176,
    			"start_time": "2026-06-05 09:56:43",
    			"end_time": "2026-06-05 10:12:25",
    			"elapsed_time": 942,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 177,
    			"start_time": "2026-06-05 10:16:05",
    			"end_time": "2026-06-05 11:32:15",
    			"elapsed_time": 4570,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 178,
    			"start_time": "2026-06-07 10:35:29",
    			"end_time": "2026-06-07 12:00:20",
    			"elapsed_time": 5091,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 179,
    			"start_time": "2026-06-08 05:40:07",
    			"end_time": "2026-06-08 07:30:15",
    			"elapsed_time": 6608,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 180,
    			"start_time": "2026-06-08 12:53:24",
    			"end_time": "2026-06-08 16:48:25",
    			"elapsed_time": 14101,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 181,
    			"start_time": "2026-06-08 19:37:58",
    			"end_time": "2026-06-09 01:00:08",
    			"elapsed_time": 19330,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 182,
    			"start_time": "2026-06-09 01:29:36",
    			"end_time": "2026-06-09 03:04:55",
    			"elapsed_time": 5719,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 183,
    			"start_time": "2026-06-20 09:26:25",
    			"end_time": "2026-06-20 11:19:47",
    			"elapsed_time": 6802,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 184,
    			"start_time": "2026-06-20 12:39:54",
    			"end_time": "2026-06-20 13:46:43",
    			"elapsed_time": 4009,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 185,
    			"start_time": "2026-06-20 17:18:04",
    			"end_time": "2026-06-20 21:25:18",
    			"elapsed_time": 14834,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 186,
    			"start_time": "2026-06-20 22:03:28",
    			"end_time": "2026-06-20 23:58:47",
    			"elapsed_time": 6919,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 187,
    			"start_time": "2026-06-21 00:13:45",
    			"end_time": "2026-06-21 01:26:16",
    			"elapsed_time": 4351,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 425,
    			"start_time": "2026-06-21 08:01:07",
    			"end_time": "2026-06-21 10:33:01",
    			"elapsed_time": 9114,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 426,
    			"start_time": "2026-06-21 10:34:28",
    			"end_time": "2026-06-21 10:41:18",
    			"elapsed_time": 410,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 429,
    			"start_time": "2026-06-21 12:15:58",
    			"end_time": "2026-06-21 13:12:20",
    			"elapsed_time": 3382,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 430,
    			"start_time": "2026-06-21 13:12:33",
    			"end_time": "2026-06-21 17:07:43",
    			"elapsed_time": 14110,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 432,
    			"start_time": "2026-06-21 21:04:35",
    			"end_time": "2026-06-22 00:01:55",
    			"elapsed_time": 10640,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 434,
    			"start_time": "2026-06-22 05:05:31",
    			"end_time": "2026-06-22 06:54:40",
    			"elapsed_time": 6549,
    			"visible": false,
    			"done": true,
    			"activity_id": 11
    		},
    		"nameActivity": "EASY TIME TRACKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 188,
    			"start_time": "2026-05-29 08:27:34",
    			"end_time": "2026-05-29 09:06:35",
    			"elapsed_time": 2341,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 189,
    			"start_time": "2026-06-01 12:14:13",
    			"end_time": "2026-06-01 13:17:50",
    			"elapsed_time": 3817,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 190,
    			"start_time": "2026-06-02 13:06:43",
    			"end_time": "2026-06-02 13:45:49",
    			"elapsed_time": 2346,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 191,
    			"start_time": "2026-06-04 08:12:43",
    			"end_time": "2026-06-04 08:32:21",
    			"elapsed_time": 1178,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 192,
    			"start_time": "2026-06-04 13:46:16",
    			"end_time": "2026-06-04 13:56:26",
    			"elapsed_time": 610,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 193,
    			"start_time": "2026-06-05 10:16:18",
    			"end_time": "2026-06-05 12:58:12",
    			"elapsed_time": 9714,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 194,
    			"start_time": "2026-06-08 11:25:52",
    			"end_time": "2026-06-08 11:42:04",
    			"elapsed_time": 972,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 195,
    			"start_time": "2026-06-08 12:13:23",
    			"end_time": "2026-06-08 12:44:55",
    			"elapsed_time": 1892,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 196,
    			"start_time": "2026-06-10 10:12:00",
    			"end_time": "2026-06-10 10:32:00",
    			"elapsed_time": 1200,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 197,
    			"start_time": "2026-06-10 13:38:22",
    			"end_time": "2026-06-10 14:45:15",
    			"elapsed_time": 4013,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 198,
    			"start_time": "2026-06-10 16:44:36",
    			"end_time": "2026-06-10 16:54:16",
    			"elapsed_time": 580,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 199,
    			"start_time": "2026-06-11 21:12:11",
    			"end_time": "2026-06-11 21:17:42",
    			"elapsed_time": 331,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 200,
    			"start_time": "2026-06-12 08:39:06",
    			"end_time": "2026-06-12 09:53:02",
    			"elapsed_time": 4436,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 201,
    			"start_time": "2026-06-12 10:20:18",
    			"end_time": "2026-06-12 10:27:35",
    			"elapsed_time": 437,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 202,
    			"start_time": "2026-06-19 10:00:11",
    			"end_time": "2026-06-19 11:36:38",
    			"elapsed_time": 5787,
    			"visible": false,
    			"done": true,
    			"activity_id": 12
    		},
    		"nameActivity": "ELEVATE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 203,
    			"start_time": "2026-05-28 10:01:50",
    			"end_time": "2026-05-28 10:19:49",
    			"elapsed_time": 1079,
    			"visible": false,
    			"done": true,
    			"activity_id": 13
    		},
    		"nameActivity": "EMAIL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 204,
    			"start_time": "2026-06-12 12:26:30",
    			"end_time": "2026-06-12 12:43:40",
    			"elapsed_time": 1030,
    			"visible": false,
    			"done": true,
    			"activity_id": 13
    		},
    		"nameActivity": "EMAIL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 205,
    			"start_time": "2026-06-18 07:16:30",
    			"end_time": "2026-06-18 08:28:20",
    			"elapsed_time": 4310,
    			"visible": false,
    			"done": true,
    			"activity_id": 13
    		},
    		"nameActivity": "EMAIL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 206,
    			"start_time": "2026-06-18 18:11:11",
    			"end_time": "2026-06-18 21:01:57",
    			"elapsed_time": 10246,
    			"visible": false,
    			"done": true,
    			"activity_id": 13
    		},
    		"nameActivity": "EMAIL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 207,
    			"start_time": "2026-06-18 23:24:41",
    			"end_time": "2026-06-18 23:39:13",
    			"elapsed_time": 872,
    			"visible": false,
    			"done": true,
    			"activity_id": 13
    		},
    		"nameActivity": "EMAIL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 208,
    			"start_time": "2026-05-21 22:24:57",
    			"end_time": "2026-05-21 23:01:37",
    			"elapsed_time": 2200,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 209,
    			"start_time": "2026-05-22 11:13:22",
    			"end_time": "2026-05-22 13:22:34",
    			"elapsed_time": 7752,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 210,
    			"start_time": "2026-05-24 12:23:09",
    			"end_time": "2026-05-24 13:00:29",
    			"elapsed_time": 2240,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 211,
    			"start_time": "2026-05-25 09:50:41",
    			"end_time": "2026-05-25 10:00:35",
    			"elapsed_time": 594,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 212,
    			"start_time": "2026-05-27 20:43:09",
    			"end_time": "2026-05-27 21:18:31",
    			"elapsed_time": 2122,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 213,
    			"start_time": "2026-05-30 07:15:43",
    			"end_time": "2026-05-30 07:23:02",
    			"elapsed_time": 439,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 214,
    			"start_time": "2026-05-30 20:36:08",
    			"end_time": "2026-05-30 21:18:41",
    			"elapsed_time": 2553,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 215,
    			"start_time": "2026-06-06 10:58:52",
    			"end_time": "2026-06-06 11:20:35",
    			"elapsed_time": 1303,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 216,
    			"start_time": "2026-06-07 19:20:35",
    			"end_time": "2026-06-07 20:04:29",
    			"elapsed_time": 2634,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 217,
    			"start_time": "2026-06-08 16:56:32",
    			"end_time": "2026-06-08 17:09:04",
    			"elapsed_time": 752,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 218,
    			"start_time": "2026-06-11 08:52:00",
    			"end_time": "2026-06-11 09:53:20",
    			"elapsed_time": 3680,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 219,
    			"start_time": "2026-06-12 07:33:25",
    			"end_time": "2026-06-12 07:51:12",
    			"elapsed_time": 1067,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 220,
    			"start_time": "2026-06-15 20:00:56",
    			"end_time": "2026-06-15 20:21:11",
    			"elapsed_time": 1215,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 221,
    			"start_time": "2026-06-19 08:27:58",
    			"end_time": "2026-06-19 08:54:50",
    			"elapsed_time": 1612,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 431,
    			"start_time": "2026-06-21 20:15:50",
    			"end_time": "2026-06-21 20:36:52",
    			"elapsed_time": 1262,
    			"visible": false,
    			"done": true,
    			"activity_id": 14
    		},
    		"nameActivity": "GET READY"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 222,
    			"start_time": "2026-05-27 10:14:32",
    			"end_time": "2026-05-27 10:33:28",
    			"elapsed_time": 1136,
    			"visible": false,
    			"done": true,
    			"activity_id": 15
    		},
    		"nameActivity": "MANIFESTATION JOURNALS"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 223,
    			"start_time": "2026-06-03 06:43:58",
    			"end_time": "2026-06-03 06:57:22",
    			"elapsed_time": 804,
    			"visible": false,
    			"done": true,
    			"activity_id": 15
    		},
    		"nameActivity": "MANIFESTATION JOURNALS"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 224,
    			"start_time": "2026-05-27 07:16:39",
    			"end_time": "2026-05-27 09:23:29",
    			"elapsed_time": 7610,
    			"visible": false,
    			"done": true,
    			"activity_id": 16
    		},
    		"nameActivity": "MEDITATION"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 225,
    			"start_time": "2026-05-28 08:42:00",
    			"end_time": "2026-05-28 08:48:26",
    			"elapsed_time": 386,
    			"visible": false,
    			"done": true,
    			"activity_id": 16
    		},
    		"nameActivity": "MEDITATION"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 226,
    			"start_time": "2026-05-30 07:23:15",
    			"end_time": "2026-05-30 07:25:01",
    			"elapsed_time": 106,
    			"visible": false,
    			"done": true,
    			"activity_id": 16
    		},
    		"nameActivity": "MEDITATION"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 227,
    			"start_time": "2026-06-09 10:41:58",
    			"end_time": "2026-06-09 10:45:18",
    			"elapsed_time": 200,
    			"visible": false,
    			"done": true,
    			"activity_id": 16
    		},
    		"nameActivity": "MEDITATION"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 228,
    			"start_time": "2026-06-10 08:56:11",
    			"end_time": "2026-06-10 09:01:55",
    			"elapsed_time": 344,
    			"visible": false,
    			"done": true,
    			"activity_id": 16
    		},
    		"nameActivity": "MEDITATION"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 229,
    			"start_time": "2026-06-13 18:13:45",
    			"end_time": "2026-06-13 18:18:26",
    			"elapsed_time": 281,
    			"visible": false,
    			"done": true,
    			"activity_id": 16
    		},
    		"nameActivity": "MEDITATION"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 230,
    			"start_time": "2026-05-23 17:52:01",
    			"end_time": "2026-05-23 17:55:55",
    			"elapsed_time": 234,
    			"visible": false,
    			"done": true,
    			"activity_id": 17
    		},
    		"nameActivity": "MERIENDA"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 231,
    			"start_time": "2026-05-26 17:35:50",
    			"end_time": "2026-05-26 18:39:00",
    			"elapsed_time": 3790,
    			"visible": false,
    			"done": true,
    			"activity_id": 17
    		},
    		"nameActivity": "MERIENDA"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 232,
    			"start_time": "2026-05-22 23:07:59",
    			"end_time": "2026-05-22 23:37:59",
    			"elapsed_time": 1800,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 233,
    			"start_time": "2026-05-24 18:22:45",
    			"end_time": "2026-05-24 19:52:02",
    			"elapsed_time": 5357,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 234,
    			"start_time": "2026-05-25 14:21:47",
    			"end_time": "2026-05-25 14:55:15",
    			"elapsed_time": 2008,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 235,
    			"start_time": "2026-05-26 19:00:15",
    			"end_time": "2026-05-26 20:59:50",
    			"elapsed_time": 7175,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 236,
    			"start_time": "2026-05-29 23:54:15",
    			"end_time": "2026-05-30 01:42:46",
    			"elapsed_time": 6511,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 237,
    			"start_time": "2026-06-01 00:03:06",
    			"end_time": "2026-06-01 01:09:38",
    			"elapsed_time": 3992,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 238,
    			"start_time": "2026-06-02 23:20:44",
    			"end_time": "2026-06-02 23:38:35",
    			"elapsed_time": 1071,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 239,
    			"start_time": "2026-06-05 00:48:55",
    			"end_time": "2026-06-05 01:02:01",
    			"elapsed_time": 786,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 240,
    			"start_time": "2026-06-11 23:39:10",
    			"end_time": "2026-06-12 00:29:32",
    			"elapsed_time": 3022,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 241,
    			"start_time": "2026-06-15 18:35:17",
    			"end_time": "2026-06-15 18:40:12",
    			"elapsed_time": 295,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 242,
    			"start_time": "2026-06-18 15:16:09",
    			"end_time": "2026-06-18 16:43:19",
    			"elapsed_time": 5230,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 243,
    			"start_time": "2026-06-18 21:02:12",
    			"end_time": "2026-06-18 23:00:21",
    			"elapsed_time": 7089,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 244,
    			"start_time": "2026-06-18 23:51:14",
    			"end_time": "2026-06-19 01:15:33",
    			"elapsed_time": 5059,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 245,
    			"start_time": "2026-06-19 13:55:59",
    			"end_time": "2026-06-19 17:16:27",
    			"elapsed_time": 12028,
    			"visible": false,
    			"done": true,
    			"activity_id": 18
    		},
    		"nameActivity": "MOVIE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 246,
    			"start_time": "2026-05-22 15:04:13",
    			"end_time": "2026-05-22 15:27:04",
    			"elapsed_time": 1371,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 247,
    			"start_time": "2026-05-23 15:48:05",
    			"end_time": "2026-05-23 17:51:32",
    			"elapsed_time": 7407,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 248,
    			"start_time": "2026-05-24 11:17:06",
    			"end_time": "2026-05-24 12:16:24",
    			"elapsed_time": 3558,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 249,
    			"start_time": "2026-05-25 08:26:48",
    			"end_time": "2026-05-25 09:50:35",
    			"elapsed_time": 5027,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 250,
    			"start_time": "2026-05-25 17:16:29",
    			"end_time": "2026-05-25 20:02:58",
    			"elapsed_time": 9989,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 251,
    			"start_time": "2026-05-26 07:13:35",
    			"end_time": "2026-05-26 09:40:14",
    			"elapsed_time": 8799,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 252,
    			"start_time": "2026-05-26 13:59:34",
    			"end_time": "2026-05-26 16:48:08",
    			"elapsed_time": 10114,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 253,
    			"start_time": "2026-05-29 16:33:52",
    			"end_time": "2026-05-29 18:12:35",
    			"elapsed_time": 5923,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 254,
    			"start_time": "2026-05-30 11:50:46",
    			"end_time": "2026-05-30 12:43:12",
    			"elapsed_time": 3146,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 255,
    			"start_time": "2026-06-04 14:45:40",
    			"end_time": "2026-06-04 15:47:32",
    			"elapsed_time": 3712,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 256,
    			"start_time": "2026-06-05 14:32:36",
    			"end_time": "2026-06-05 15:05:08",
    			"elapsed_time": 1952,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 257,
    			"start_time": "2026-06-07 12:01:47",
    			"end_time": "2026-06-07 16:20:00",
    			"elapsed_time": 15493,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 258,
    			"start_time": "2026-06-09 09:34:19",
    			"end_time": "2026-06-09 10:34:52",
    			"elapsed_time": 3633,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 259,
    			"start_time": "2026-06-10 16:01:49",
    			"end_time": "2026-06-10 16:43:55",
    			"elapsed_time": 2526,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 260,
    			"start_time": "2026-06-11 18:00:43",
    			"end_time": "2026-06-11 18:58:49",
    			"elapsed_time": 3486,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 261,
    			"start_time": "2026-06-17 07:51:58",
    			"end_time": "2026-06-17 09:39:14",
    			"elapsed_time": 6436,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 262,
    			"start_time": "2026-06-18 10:39:43",
    			"end_time": "2026-06-18 11:52:08",
    			"elapsed_time": 4345,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 263,
    			"start_time": "2026-06-19 17:16:33",
    			"end_time": "2026-06-19 19:22:12",
    			"elapsed_time": 7539,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 264,
    			"start_time": "2026-06-20 16:17:59",
    			"end_time": "2026-06-20 17:18:07",
    			"elapsed_time": 3608,
    			"visible": false,
    			"done": true,
    			"activity_id": 19
    		},
    		"nameActivity": "NAP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 265,
    			"start_time": "2026-06-06 08:20:20",
    			"end_time": "2026-06-06 09:56:52",
    			"elapsed_time": 5792,
    			"visible": false,
    			"done": true,
    			"activity_id": 20
    		},
    		"nameActivity": "ORGANIZE DATA"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 266,
    			"start_time": "2026-06-12 13:09:54",
    			"end_time": "2026-06-12 16:17:09",
    			"elapsed_time": 11235,
    			"visible": false,
    			"done": true,
    			"activity_id": 20
    		},
    		"nameActivity": "ORGANIZE DATA"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 267,
    			"start_time": "2026-05-23 15:27:26",
    			"end_time": "2026-05-23 15:41:30",
    			"elapsed_time": 844,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 268,
    			"start_time": "2026-05-23 15:46:59",
    			"end_time": "2026-05-23 15:47:52",
    			"elapsed_time": 53,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 269,
    			"start_time": "2026-05-23 18:56:43",
    			"end_time": "2026-05-23 21:16:37",
    			"elapsed_time": 8394,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 270,
    			"start_time": "2026-05-23 21:16:50",
    			"end_time": "2026-05-23 21:32:32",
    			"elapsed_time": 942,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 271,
    			"start_time": "2026-05-23 22:09:08",
    			"end_time": "2026-05-23 22:10:39",
    			"elapsed_time": 91,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 272,
    			"start_time": "2026-05-28 17:44:04",
    			"end_time": "2026-05-28 19:18:17",
    			"elapsed_time": 5653,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 273,
    			"start_time": "2026-05-28 21:12:55",
    			"end_time": "2026-05-28 21:23:28",
    			"elapsed_time": 633,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 274,
    			"start_time": "2026-05-28 22:18:57",
    			"end_time": "2026-05-28 23:33:00",
    			"elapsed_time": 4443,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 275,
    			"start_time": "2026-05-29 09:34:46",
    			"end_time": "2026-05-29 12:52:32",
    			"elapsed_time": 11866,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 276,
    			"start_time": "2026-05-29 16:34:05",
    			"end_time": "2026-05-29 22:03:29",
    			"elapsed_time": 19764,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 277,
    			"start_time": "2026-05-30 07:25:07",
    			"end_time": "2026-05-30 08:36:15",
    			"elapsed_time": 4268,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 278,
    			"start_time": "2026-06-10 09:28:00",
    			"end_time": "2026-06-10 09:41:07",
    			"elapsed_time": 787,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 279,
    			"start_time": "2026-06-11 21:58:44",
    			"end_time": "2026-06-11 22:14:03",
    			"elapsed_time": 919,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 280,
    			"start_time": "2026-06-20 08:14:40",
    			"end_time": "2026-06-20 09:24:03",
    			"elapsed_time": 4163,
    			"visible": false,
    			"done": true,
    			"activity_id": 21
    		},
    		"nameActivity": "PEIMI"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 281,
    			"start_time": "2026-05-26 10:08:41",
    			"end_time": "2026-05-26 11:49:14",
    			"elapsed_time": 6033,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 282,
    			"start_time": "2026-05-27 11:17:06",
    			"end_time": "2026-05-27 13:22:15",
    			"elapsed_time": 7509,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 283,
    			"start_time": "2026-06-01 08:25:36",
    			"end_time": "2026-06-01 09:05:30",
    			"elapsed_time": 2394,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 284,
    			"start_time": "2026-06-02 11:08:50",
    			"end_time": "2026-06-02 12:09:03",
    			"elapsed_time": 3613,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 285,
    			"start_time": "2026-06-04 08:32:26",
    			"end_time": "2026-06-04 08:57:57",
    			"elapsed_time": 1531,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 286,
    			"start_time": "2026-06-04 18:37:29",
    			"end_time": "2026-06-04 18:42:07",
    			"elapsed_time": 278,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 287,
    			"start_time": "2026-06-05 13:51:22",
    			"end_time": "2026-06-05 14:11:42",
    			"elapsed_time": 1220,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 288,
    			"start_time": "2026-06-08 12:01:57",
    			"end_time": "2026-06-08 12:13:17",
    			"elapsed_time": 680,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 289,
    			"start_time": "2026-06-08 12:45:06",
    			"end_time": "2026-06-08 12:53:17",
    			"elapsed_time": 491,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 290,
    			"start_time": "2026-06-08 19:33:51",
    			"end_time": "2026-06-08 19:37:47",
    			"elapsed_time": 236,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 291,
    			"start_time": "2026-06-08 20:09:03",
    			"end_time": "2026-06-08 20:18:13",
    			"elapsed_time": 550,
    			"visible": false,
    			"done": true,
    			"activity_id": 22
    		},
    		"nameActivity": "PRIME HOME"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 292,
    			"start_time": "2026-05-31 12:05:42",
    			"end_time": "2026-05-31 14:20:25",
    			"elapsed_time": 8083,
    			"visible": false,
    			"done": true,
    			"activity_id": 23
    		},
    		"nameActivity": "READ BOOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 293,
    			"start_time": "2026-06-01 13:18:06",
    			"end_time": "2026-06-01 13:25:32",
    			"elapsed_time": 446,
    			"visible": false,
    			"done": true,
    			"activity_id": 24
    		},
    		"nameActivity": "RELAX"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 294,
    			"start_time": "2026-05-22 02:56:30",
    			"end_time": "2026-05-22 07:02:13",
    			"elapsed_time": 14743,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 295,
    			"start_time": "2026-05-23 23:51:36",
    			"end_time": "2026-05-24 06:25:53",
    			"elapsed_time": 23657,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 296,
    			"start_time": "2026-05-24 22:47:19",
    			"end_time": "2026-05-25 06:11:47",
    			"elapsed_time": 26668,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 297,
    			"start_time": "2026-05-25 22:36:36",
    			"end_time": "2026-05-26 05:34:40",
    			"elapsed_time": 25084,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 298,
    			"start_time": "2026-05-26 23:57:14",
    			"end_time": "2026-05-27 06:15:46",
    			"elapsed_time": 22712,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 299,
    			"start_time": "2026-05-27 22:46:05",
    			"end_time": "2026-05-28 04:17:34",
    			"elapsed_time": 19889,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 300,
    			"start_time": "2026-05-28 05:05:02",
    			"end_time": "2026-05-28 08:41:52",
    			"elapsed_time": 13010,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 301,
    			"start_time": "2026-05-29 01:48:00",
    			"end_time": "2026-05-29 07:20:32",
    			"elapsed_time": 19952,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 302,
    			"start_time": "2026-05-30 02:51:06",
    			"end_time": "2026-05-30 07:15:38",
    			"elapsed_time": 15872,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 303,
    			"start_time": "2026-05-31 01:11:01",
    			"end_time": "2026-05-31 06:41:36",
    			"elapsed_time": 19835,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 304,
    			"start_time": "2026-06-01 01:09:44",
    			"end_time": "2026-06-01 07:42:12",
    			"elapsed_time": 23548,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 305,
    			"start_time": "2026-06-01 23:39:22",
    			"end_time": "2026-06-02 08:50:56",
    			"elapsed_time": 33094,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 306,
    			"start_time": "2026-06-03 01:04:57",
    			"end_time": "2026-06-03 06:04:00",
    			"elapsed_time": 17943,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 307,
    			"start_time": "2026-06-04 01:39:56",
    			"end_time": "2026-06-04 06:44:32",
    			"elapsed_time": 18276,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 308,
    			"start_time": "2026-06-05 02:25:47",
    			"end_time": "2026-06-05 07:21:57",
    			"elapsed_time": 17770,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 309,
    			"start_time": "2026-06-06 03:30:22",
    			"end_time": "2026-06-06 06:17:36",
    			"elapsed_time": 10034,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 310,
    			"start_time": "2026-06-07 01:20:39",
    			"end_time": "2026-06-07 07:41:11",
    			"elapsed_time": 22832,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 311,
    			"start_time": "2026-06-08 00:00:01",
    			"end_time": "2026-06-08 05:36:00",
    			"elapsed_time": 20159,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 312,
    			"start_time": "2026-06-08 07:30:21",
    			"end_time": "2026-06-08 10:04:41",
    			"elapsed_time": 9260,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 313,
    			"start_time": "2026-06-09 03:59:26",
    			"end_time": "2026-06-09 07:56:43",
    			"elapsed_time": 14237,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 314,
    			"start_time": "2026-06-10 00:27:09",
    			"end_time": "2026-06-10 08:55:04",
    			"elapsed_time": 30475,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 315,
    			"start_time": "2026-06-11 02:46:43",
    			"end_time": "2026-06-11 08:47:47",
    			"elapsed_time": 21664,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 316,
    			"start_time": "2026-06-12 01:07:19",
    			"end_time": "2026-06-12 07:27:23",
    			"elapsed_time": 22804,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 317,
    			"start_time": "2026-06-13 03:10:53",
    			"end_time": "2026-06-13 08:06:57",
    			"elapsed_time": 17764,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 318,
    			"start_time": "2026-06-16 00:04:31",
    			"end_time": "2026-06-16 05:02:59",
    			"elapsed_time": 17908,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 319,
    			"start_time": "2026-06-17 00:01:32",
    			"end_time": "2026-06-17 05:06:24",
    			"elapsed_time": 18292,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 320,
    			"start_time": "2026-06-18 00:04:21",
    			"end_time": "2026-06-18 06:12:45",
    			"elapsed_time": 22104,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 321,
    			"start_time": "2026-06-19 01:21:03",
    			"end_time": "2026-06-19 08:21:04",
    			"elapsed_time": 25201,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 322,
    			"start_time": "2026-06-19 23:47:10",
    			"end_time": "2026-06-20 04:46:48",
    			"elapsed_time": 17978,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 424,
    			"start_time": "2026-06-21 01:30:09",
    			"end_time": "2026-06-21 07:59:14",
    			"elapsed_time": 23345,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 433,
    			"start_time": "2026-06-22 00:10:08",
    			"end_time": "2026-06-22 05:00:34",
    			"elapsed_time": 17426,
    			"visible": false,
    			"done": true,
    			"activity_id": 25
    		},
    		"nameActivity": "SLEEP"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 323,
    			"start_time": "2026-06-05 21:03:33",
    			"end_time": "2026-06-06 03:29:14",
    			"elapsed_time": 23141,
    			"visible": false,
    			"done": true,
    			"activity_id": 26
    		},
    		"nameActivity": "SOCIAL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 324,
    			"start_time": "2026-06-12 17:58:58",
    			"end_time": "2026-06-13 02:51:46",
    			"elapsed_time": 31968,
    			"visible": false,
    			"done": true,
    			"activity_id": 26
    		},
    		"nameActivity": "SOCIAL"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 325,
    			"start_time": "2026-05-31 17:28:42",
    			"end_time": "2026-05-31 19:15:50",
    			"elapsed_time": 6428,
    			"visible": false,
    			"done": true,
    			"activity_id": 27
    		},
    		"nameActivity": "SUPERMARKET"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 326,
    			"start_time": "2026-06-05 18:36:49",
    			"end_time": "2026-06-05 19:37:50",
    			"elapsed_time": 3661,
    			"visible": false,
    			"done": true,
    			"activity_id": 27
    		},
    		"nameActivity": "SUPERMARKET"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 327,
    			"start_time": "2026-05-22 23:53:25",
    			"end_time": "2026-05-23 00:02:10",
    			"elapsed_time": 525,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 328,
    			"start_time": "2026-05-23 11:56:39",
    			"end_time": "2026-05-23 13:41:39",
    			"elapsed_time": 6300,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 329,
    			"start_time": "2026-05-24 11:10:39",
    			"end_time": "2026-05-24 11:17:01",
    			"elapsed_time": 382,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 330,
    			"start_time": "2026-05-24 12:16:29",
    			"end_time": "2026-05-24 12:23:04",
    			"elapsed_time": 395,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 331,
    			"start_time": "2026-05-24 21:29:17",
    			"end_time": "2026-05-24 21:46:27",
    			"elapsed_time": 1030,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 332,
    			"start_time": "2026-05-25 07:00:15",
    			"end_time": "2026-05-25 08:26:45",
    			"elapsed_time": 5190,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 333,
    			"start_time": "2026-05-25 10:27:58",
    			"end_time": "2026-05-25 11:11:01",
    			"elapsed_time": 2583,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 334,
    			"start_time": "2026-05-25 22:08:06",
    			"end_time": "2026-05-25 22:36:31",
    			"elapsed_time": 1705,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 335,
    			"start_time": "2026-05-26 22:21:39",
    			"end_time": "2026-05-26 23:57:09",
    			"elapsed_time": 5730,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 336,
    			"start_time": "2026-05-27 14:27:54",
    			"end_time": "2026-05-27 15:49:53",
    			"elapsed_time": 4919,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 337,
    			"start_time": "2026-05-27 18:54:34",
    			"end_time": "2026-05-27 20:43:04",
    			"elapsed_time": 6510,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 338,
    			"start_time": "2026-05-28 08:48:31",
    			"end_time": "2026-05-28 09:10:11",
    			"elapsed_time": 1300,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 339,
    			"start_time": "2026-05-30 01:42:49",
    			"end_time": "2026-05-30 02:51:03",
    			"elapsed_time": 4094,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 340,
    			"start_time": "2026-06-01 19:55:02",
    			"end_time": "2026-06-01 21:56:12",
    			"elapsed_time": 7270,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 341,
    			"start_time": "2026-06-05 01:04:34",
    			"end_time": "2026-06-05 02:25:39",
    			"elapsed_time": 4865,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 342,
    			"start_time": "2026-06-05 15:32:53",
    			"end_time": "2026-06-05 16:16:37",
    			"elapsed_time": 2624,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 343,
    			"start_time": "2026-06-06 06:40:09",
    			"end_time": "2026-06-06 07:39:43",
    			"elapsed_time": 3574,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 344,
    			"start_time": "2026-06-09 03:05:22",
    			"end_time": "2026-06-09 03:59:19",
    			"elapsed_time": 3237,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 345,
    			"start_time": "2026-06-09 22:26:57",
    			"end_time": "2026-06-10 00:27:02",
    			"elapsed_time": 7205,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 346,
    			"start_time": "2026-06-10 14:45:32",
    			"end_time": "2026-06-10 16:00:38",
    			"elapsed_time": 4506,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 347,
    			"start_time": "2026-06-10 17:10:51",
    			"end_time": "2026-06-10 17:59:14",
    			"elapsed_time": 2903,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 348,
    			"start_time": "2026-06-11 02:16:05",
    			"end_time": "2026-06-11 02:46:32",
    			"elapsed_time": 1827,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 349,
    			"start_time": "2026-06-11 12:00:03",
    			"end_time": "2026-06-11 12:25:10",
    			"elapsed_time": 1507,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 350,
    			"start_time": "2026-06-12 00:29:40",
    			"end_time": "2026-06-12 01:07:14",
    			"elapsed_time": 2254,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 351,
    			"start_time": "2026-06-16 05:03:19",
    			"end_time": "2026-06-16 06:10:44",
    			"elapsed_time": 4045,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 352,
    			"start_time": "2026-06-18 17:32:50",
    			"end_time": "2026-06-18 18:10:54",
    			"elapsed_time": 2284,
    			"visible": false,
    			"done": true,
    			"activity_id": 28
    		},
    		"nameActivity": "TIKTOK"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 353,
    			"start_time": "2026-06-12 16:17:14",
    			"end_time": "2026-06-12 16:32:33",
    			"elapsed_time": 919,
    			"visible": false,
    			"done": true,
    			"activity_id": 29
    		},
    		"nameActivity": "TRANSPORT"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 354,
    			"start_time": "2026-06-13 18:02:13",
    			"end_time": "2026-06-13 18:18:29",
    			"elapsed_time": 976,
    			"visible": false,
    			"done": true,
    			"activity_id": 29
    		},
    		"nameActivity": "TRANSPORT"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 355,
    			"start_time": "2026-06-16 14:53:13",
    			"end_time": "2026-06-16 15:22:57",
    			"elapsed_time": 1784,
    			"visible": false,
    			"done": true,
    			"activity_id": 29
    		},
    		"nameActivity": "TRANSPORT"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 356,
    			"start_time": "2026-06-16 17:55:45",
    			"end_time": "2026-06-16 18:09:16",
    			"elapsed_time": 811,
    			"visible": false,
    			"done": true,
    			"activity_id": 29
    		},
    		"nameActivity": "TRANSPORT"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 357,
    			"start_time": "2026-05-22 05:54:38",
    			"end_time": "2026-05-22 07:02:06",
    			"elapsed_time": 4048,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 358,
    			"start_time": "2026-05-23 17:56:00",
    			"end_time": "2026-05-23 18:56:37",
    			"elapsed_time": 3637,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 359,
    			"start_time": "2026-05-24 10:48:39",
    			"end_time": "2026-05-24 11:10:34",
    			"elapsed_time": 1315,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 360,
    			"start_time": "2026-05-24 15:55:16",
    			"end_time": "2026-05-24 16:23:03",
    			"elapsed_time": 1667,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 361,
    			"start_time": "2026-05-24 16:55:15",
    			"end_time": "2026-05-24 20:19:28",
    			"elapsed_time": 12253,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 362,
    			"start_time": "2026-05-25 06:13:10",
    			"end_time": "2026-05-25 07:00:09",
    			"elapsed_time": 2819,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 363,
    			"start_time": "2026-05-25 10:00:38",
    			"end_time": "2026-05-25 10:27:53",
    			"elapsed_time": 1635,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 364,
    			"start_time": "2026-05-25 20:42:19",
    			"end_time": "2026-05-25 22:08:01",
    			"elapsed_time": 5142,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 365,
    			"start_time": "2026-05-26 06:10:56",
    			"end_time": "2026-05-26 07:13:31",
    			"elapsed_time": 3755,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 366,
    			"start_time": "2026-05-26 16:48:14",
    			"end_time": "2026-05-26 17:35:44",
    			"elapsed_time": 2850,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 367,
    			"start_time": "2026-05-26 20:59:56",
    			"end_time": "2026-05-26 22:21:34",
    			"elapsed_time": 4898,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 368,
    			"start_time": "2026-05-27 06:15:49",
    			"end_time": "2026-05-27 07:15:56",
    			"elapsed_time": 3607,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 369,
    			"start_time": "2026-05-27 13:22:26",
    			"end_time": "2026-05-27 14:27:48",
    			"elapsed_time": 3922,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 370,
    			"start_time": "2026-05-28 04:20:43",
    			"end_time": "2026-05-28 05:04:58",
    			"elapsed_time": 2655,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 371,
    			"start_time": "2026-05-28 16:06:09",
    			"end_time": "2026-05-28 16:47:38",
    			"elapsed_time": 2489,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 372,
    			"start_time": "2026-05-29 22:03:25",
    			"end_time": "2026-05-29 23:01:07",
    			"elapsed_time": 3462,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 373,
    			"start_time": "2026-05-30 12:43:15",
    			"end_time": "2026-05-30 18:04:09",
    			"elapsed_time": 19254,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 374,
    			"start_time": "2026-05-30 19:09:35",
    			"end_time": "2026-05-30 20:35:59",
    			"elapsed_time": 5184,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 375,
    			"start_time": "2026-05-31 06:47:49",
    			"end_time": "2026-05-31 07:03:10",
    			"elapsed_time": 921,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 376,
    			"start_time": "2026-05-31 09:35:00",
    			"end_time": "2026-05-31 12:05:00",
    			"elapsed_time": 9000,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 377,
    			"start_time": "2026-05-31 14:47:44",
    			"end_time": "2026-05-31 16:05:42",
    			"elapsed_time": 4678,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 378,
    			"start_time": "2026-05-31 19:16:01",
    			"end_time": "2026-05-31 20:34:22",
    			"elapsed_time": 4701,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 379,
    			"start_time": "2026-05-31 22:59:53",
    			"end_time": "2026-05-31 22:59:56",
    			"elapsed_time": 3,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 380,
    			"start_time": "2026-06-01 14:59:34",
    			"end_time": "2026-06-01 15:13:58",
    			"elapsed_time": 864,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 381,
    			"start_time": "2026-06-01 15:14:05",
    			"end_time": "2026-06-01 17:08:33",
    			"elapsed_time": 6868,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 382,
    			"start_time": "2026-06-02 16:15:03",
    			"end_time": "2026-06-02 17:10:22",
    			"elapsed_time": 3319,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 383,
    			"start_time": "2026-06-02 17:56:53",
    			"end_time": "2026-06-02 18:54:15",
    			"elapsed_time": 3442,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 384,
    			"start_time": "2026-06-03 00:45:57",
    			"end_time": "2026-06-03 01:04:47",
    			"elapsed_time": 1130,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 385,
    			"start_time": "2026-06-03 06:04:12",
    			"end_time": "2026-06-03 06:36:52",
    			"elapsed_time": 1960,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 386,
    			"start_time": "2026-06-03 09:59:33",
    			"end_time": "2026-06-03 11:00:17",
    			"elapsed_time": 3644,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 387,
    			"start_time": "2026-06-03 15:55:19",
    			"end_time": "2026-06-03 16:30:57",
    			"elapsed_time": 2138,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 388,
    			"start_time": "2026-06-05 01:05:02",
    			"end_time": "2026-06-05 02:25:33",
    			"elapsed_time": 4831,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 389,
    			"start_time": "2026-06-05 15:05:35",
    			"end_time": "2026-06-05 15:32:45",
    			"elapsed_time": 1630,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 390,
    			"start_time": "2026-06-07 07:50:12",
    			"end_time": "2026-06-07 08:06:15",
    			"elapsed_time": 963,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 391,
    			"start_time": "2026-06-07 20:04:50",
    			"end_time": "2026-06-07 20:23:00",
    			"elapsed_time": 1090,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 392,
    			"start_time": "2026-06-08 19:07:29",
    			"end_time": "2026-06-08 19:33:07",
    			"elapsed_time": 1538,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 393,
    			"start_time": "2026-06-09 08:07:28",
    			"end_time": "2026-06-09 08:30:50",
    			"elapsed_time": 1402,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 394,
    			"start_time": "2026-06-09 09:13:36",
    			"end_time": "2026-06-09 09:34:15",
    			"elapsed_time": 1239,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 395,
    			"start_time": "2026-06-10 18:00:12",
    			"end_time": "2026-06-10 20:14:29",
    			"elapsed_time": 8057,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 396,
    			"start_time": "2026-06-11 09:53:40",
    			"end_time": "2026-06-11 10:50:38",
    			"elapsed_time": 3418,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 397,
    			"start_time": "2026-06-11 10:51:01",
    			"end_time": "2026-06-11 11:59:47",
    			"elapsed_time": 4126,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 398,
    			"start_time": "2026-06-11 21:28:22",
    			"end_time": "2026-06-11 21:58:27",
    			"elapsed_time": 1805,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 399,
    			"start_time": "2026-06-11 22:14:10",
    			"end_time": "2026-06-11 22:52:25",
    			"elapsed_time": 2295,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 400,
    			"start_time": "2026-06-15 20:21:17",
    			"end_time": "2026-06-16 00:01:32",
    			"elapsed_time": 13215,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 401,
    			"start_time": "2026-06-16 06:10:53",
    			"end_time": "2026-06-16 07:22:44",
    			"elapsed_time": 4311,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 402,
    			"start_time": "2026-06-17 05:06:39",
    			"end_time": "2026-06-17 06:33:32",
    			"elapsed_time": 5213,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 403,
    			"start_time": "2026-06-19 11:48:55",
    			"end_time": "2026-06-19 11:53:57",
    			"elapsed_time": 302,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 404,
    			"start_time": "2026-06-19 19:40:43",
    			"end_time": "2026-06-19 20:14:44",
    			"elapsed_time": 2041,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 405,
    			"start_time": "2026-06-19 20:16:35",
    			"end_time": "2026-06-19 22:12:48",
    			"elapsed_time": 6973,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 406,
    			"start_time": "2026-06-19 23:21:11",
    			"end_time": "2026-06-19 23:47:05",
    			"elapsed_time": 1554,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 407,
    			"start_time": "2026-06-20 14:30:12",
    			"end_time": "2026-06-20 16:03:01",
    			"elapsed_time": 5569,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 427,
    			"start_time": "2026-06-21 10:41:52",
    			"end_time": "2026-06-21 11:59:25",
    			"elapsed_time": 4653,
    			"visible": false,
    			"done": true,
    			"activity_id": 30
    		},
    		"nameActivity": "VIDEOGAMES"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 408,
    			"start_time": "2026-06-06 11:23:04",
    			"end_time": "2026-06-06 12:28:37",
    			"elapsed_time": 3933,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 409,
    			"start_time": "2026-06-08 17:09:13",
    			"end_time": "2026-06-08 18:57:58",
    			"elapsed_time": 6525,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 410,
    			"start_time": "2026-06-09 15:32:35",
    			"end_time": "2026-06-09 16:16:05",
    			"elapsed_time": 2610,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 411,
    			"start_time": "2026-06-09 17:52:24",
    			"end_time": "2026-06-09 21:07:25",
    			"elapsed_time": 11701,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 412,
    			"start_time": "2026-06-13 02:51:51",
    			"end_time": "2026-06-13 03:04:04",
    			"elapsed_time": 733,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 413,
    			"start_time": "2026-06-13 17:39:25",
    			"end_time": "2026-06-13 18:00:55",
    			"elapsed_time": 1290,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 414,
    			"start_time": "2026-06-13 18:18:36",
    			"end_time": "2026-06-13 18:38:01",
    			"elapsed_time": 1165,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 415,
    			"start_time": "2026-06-15 18:13:49",
    			"end_time": "2026-06-15 18:31:27",
    			"elapsed_time": 1058,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 416,
    			"start_time": "2026-06-16 14:27:44",
    			"end_time": "2026-06-16 14:52:37",
    			"elapsed_time": 1493,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 417,
    			"start_time": "2026-06-16 15:23:28",
    			"end_time": "2026-06-16 15:36:19",
    			"elapsed_time": 771,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 418,
    			"start_time": "2026-06-16 17:42:52",
    			"end_time": "2026-06-16 17:55:41",
    			"elapsed_time": 769,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 419,
    			"start_time": "2026-06-16 19:57:51",
    			"end_time": "2026-06-16 20:28:35",
    			"elapsed_time": 1844,
    			"visible": false,
    			"done": true,
    			"activity_id": 31
    		},
    		"nameActivity": "WALKING"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 420,
    			"start_time": "2026-05-23 15:13:21",
    			"end_time": "2026-05-23 15:27:01",
    			"elapsed_time": 820,
    			"visible": false,
    			"done": true,
    			"activity_id": 32
    		},
    		"nameActivity": "YOUTUBE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 421,
    			"start_time": "2026-05-28 19:18:20",
    			"end_time": "2026-05-28 20:49:31",
    			"elapsed_time": 5471,
    			"visible": false,
    			"done": true,
    			"activity_id": 32
    		},
    		"nameActivity": "YOUTUBE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 422,
    			"start_time": "2026-06-03 00:09:06",
    			"end_time": "2026-06-03 00:45:50",
    			"elapsed_time": 2204,
    			"visible": false,
    			"done": true,
    			"activity_id": 32
    		},
    		"nameActivity": "YOUTUBE"
    	},
    	{
    		"timerTracking": {
    			"timer_tracking_id": 423,
    			"start_time": "2026-06-15 19:22:25",
    			"end_time": "2026-06-15 20:00:22",
    			"elapsed_time": 2277,
    			"visible": false,
    			"done": true,
    			"activity_id": 32
    		},
    		"nameActivity": "YOUTUBE"
    	}
    ]
""".trimIndent()
    fun allTimerTrackingQuery(): List<TimerTrackingQuery> {
        val adapter = timerTrackingQueryAdapter.adapter
        val timers = adapter.fromJson(timerTrackingQueryJson)
        return timers ?: emptyList()
    }


}