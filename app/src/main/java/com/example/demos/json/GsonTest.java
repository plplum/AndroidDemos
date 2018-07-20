package com.example.demos.json;
import java.util.List;

import com.google.gson.Gson;


public class GsonTest {
	
	private static String data = "{"
			+ "    \"action\": \"getMixWeather\","
    + "   \"code\": 01,"
    + "   \"msg\": \"success\","
    + "   \"detail\": {"
        + "   \"latest\": {"
            + "   \"datatime\": \"2015-11-17 05:34:22\","
            + "   \"at\": \"17.7\","
            + "   \"wd\": \"117\","
            + "   \"ws\": \"2.9\","
            + "   \"ah\": \"75.4\""
            + "   },"
            + "   \"stat\": {"
            + "   \"TOTALCOUNT\": 3,"
            + "   \"ROOT\": ["
                + "   {"
                    + "   \"min\": 15,"
                    + "   \"max\": 18,"
                    + "   \"datatime\": \"2015-11-11\","
                    + "   \"mpointid\": \"200000084\","
                    + "   \"avg\": 16.6"
                    + "   },"
                + "   {"
                    + "   \"min\": 15,"
                    + "   \"max\": 19,"
                    + "   \"datatime\": \"2015-11-12\","
                    + "   \"mpointid\": \"200000084\","
                    + "   \"avg\": 17"
                    + "   },"
                + "   {"
                 + "      \"min\": 15,"
                 + "      \"max\": 17,"
                + "       \"datatime\": \"2015-11-13\","
                + "       \"mpointid\": \"200000084\","
                 + "      \"avg\": 16.2"
                 + "    }"
            + "   ]"
            + "    }"
            + "    }"
            + "   }";
	
	
	
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		
		String code = gson.fromJson(data, JsonBean.class).getCode();
		
		System.out.println("code = " + code);
		
		String ah = gson.fromJson(data, JsonBean.class).getDetail().getLatest().getAh();
		
		System.out.println("ah = " + ah);
		
		List<GsonTest.JsonBean.Detail.Stat.ROOT> listRoot = gson.fromJson(data, JsonBean.class).getDetail().getStat().getRoot();
		for (GsonTest.JsonBean.Detail.Stat.ROOT root : listRoot) {
			System.out.println("root-avg = " + root.getAvg());
		}
		
	}
	
	
	
	private class JsonBean {
		private String action;
		private String code;
		private String msg;
		private Detail detail;
		
		public String getAction() {
			return action;
		}


		public void setAction(String action) {
			this.action = action;
		}


		public String getCode() {
			return code;
		}


		public void setCode(String code) {
			this.code = code;
		}


		public String getMsg() {
			return msg;
		}


		public void setMsg(String msg) {
			this.msg = msg;
		}


		public Detail getDetail() {
			return detail;
		}


		public void setDetail(Detail detail) {
			this.detail = detail;
		}


		class Detail {
			private Latest latest;
			private Stat stat;
			
			public Latest getLatest() {
				return latest;
			}

			public void setLatest(Latest latest) {
				this.latest = latest;
			}

			public Stat getStat() {
				return stat;
			}

			public void setStat(Stat stat) {
				this.stat = stat;
			}

			class Latest {
				private String datatime;
				private String at;
				private String wd;
				private String ws;
				private String ah;
				public String getDatatime() {
					return datatime;
				}
				public void setDatatime(String datatime) {
					this.datatime = datatime;
				}
				public String getAt() {
					return at;
				}
				public void setAt(String at) {
					this.at = at;
				}
				public String getWd() {
					return wd;
				}
				public void setWd(String wd) {
					this.wd = wd;
				}
				public String getWs() {
					return ws;
				}
				public void setWs(String ws) {
					this.ws = ws;
				}
				public String getAh() {
					return ah;
				}
				public void setAh(String ah) {
					this.ah = ah;
				}
				
			}
			
			class Stat {
				private String TOTALCOUNT;
				private List<ROOT> ROOT;
				
				public String getTOTALCOUNT() {
					return TOTALCOUNT;
				}


				public void setTOTALCOUNT(String tOTALCOUNT) {
					TOTALCOUNT = tOTALCOUNT;
				}


				public List<ROOT> getRoot() {
					return ROOT;
				}


				public void setRoot(List<ROOT> root) {
					this.ROOT = root;
				}

				class ROOT {
					private String min;
					private String max;
					private String datatime;
					private String mpointid;
					private String avg;
					public String getMin() {
						return min;
					}
					public void setMin(String min) {
						this.min = min;
					}
					public String getMax() {
						return max;
					}
					public void setMax(String max) {
						this.max = max;
					}
					public String getDatatime() {
						return datatime;
					}
					public void setDatatime(String datatime) {
						this.datatime = datatime;
					}
					public String getMpointid() {
						return mpointid;
					}
					public void setMpointid(String mpointid) {
						this.mpointid = mpointid;
					}
					public String getAvg() {
						return avg;
					}
					public void setAvg(String avg) {
						this.avg = avg;
					}
					
				}
			}
			
		}
		
		
	}
	
	
}
