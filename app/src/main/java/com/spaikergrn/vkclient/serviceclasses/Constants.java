package com.spaikergrn.vkclient.serviceclasses;

public final class Constants {

    public static final String PARSE_ERROR_TAG = "Parse Error";
    public static final String MY_TAG = "myLog";
    public static final String AUTHORIZATION_URL = "https://oauth.vk.com/authorize?client_id=6241224&display=" +
            "mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=friends,photos,audio,video,pages,status," +
            "notes,messages,wall,groups,offline&response_type=token&v=5.68&state=123456";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String FWD_MESSAGES = "Forward messages";
    public static final String WALL_POST = "Wall post";
    public static final String ERROR = "Error";
    public static final String UNKNOWN_JSON_RESPONSE = "Unknown json response ";
    public static final String LANGUAGE = "ru";
    public static final String UTF_8 = "UTF-8";
    public static final String DELIMITER = ",";
    static final String ATTACHMENT = "Attachment ";
    public static final String ERROR_TO_SEND_MESSAGE = "Error to send message";
    public static final int COUNT_20 = 20;
    public static final String LONG_POLL_BROADCAST = "servicebackbroadcast";
    public static final String MTS_KEY = "mTsKey";
    public static final String AUDIO_URL = "audio_url";
    public static final String USERS_DB = "UsersDb";
    public static final String VK_CLIENT_DB = "vk_client.db";
    public static final String TABLE_TEMPLATE =
            "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY,%s)";
    public static final int ERROR_CODE_AUTH = 5;
    public static final String RELOAD = "reload";
    public static final String HTTPS_VK_COM = "https://vk.com";
    public static final int VIBRATION_DURATIONS = 400;
    public static final String FULL_SCREEN_IMAGE_VIEW = "Image URL";
    public static final int HTTP_CLIENT_TIMEOUT = 40000;
    public static final String FORWARD_MESSAGES_INTENT = "forward messages intent";
    public static final String USERS_LIST_BUNDLE = "Users list bundle";
    public static final int SLEEP_TIME = 10000;
    public static final String ANDROID_NET_CONN_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String ANDROID_NET_WIFI_WIFI_STATE_CHANGED = "android.net.wifi.WIFI_STATE_CHANGED";
    public static final String COULD_NOT_CONNECT_TO_INTERNET = "Could not Connect to internet";
    public static final String VKMODELUSER_FIREBASE = "VkModelUser";
    public static final String FILE_NOT_EXIST = "File not exist";
    public static final String REFRESH_NEWS = "Refresh News";
    public static final String REFRESH_DIALOGS = "Refresh Dialogs";

    public interface PreferencesKeys {

        String PHOTO_SIZE = "photo_size";
        String NOTIFICATIONS_NEW_MESSAGE = "notifications_new_message";
        String NOTIFICATIONS_NEW_MESSAGE_RINGTONE = "notifications_new_message_ringtone";
        String NOTIFICATIONS_NEW_MESSAGE_VIBRATE = "notifications_new_message_vibrate";
        String ENABLE_PHOTO = "enable_photo";
        String PREFERENCES_PROFILE_INFO = "Preferences profile info";
    }
    public interface Db{

        String TEXT = "TEXT";
        String BIGINT = "BIGINT";
        String INTEGER = "INTEGER";
    }

    public interface Tabs{

        String PROFILE = "PROFILE";
        String NEWS = "NEWS";
        String MESSAGES = "MESSAGES";
    }

    public interface LoadersKeys{

        int LOADER_USERS_BY_ID = 1;
        int NEWS_LOADER_ID = 2;
        int DIALOGS_LOADER_ID = 3;
    }

    public interface ImgLoader{

        String TEMP_IMAGE_POSTFIX = ".tmp";
        String HASH_ALGORITHM = "MD5";
        String IMG_CACHE_FOLDER = "imgcache";
        String BITMAP_IS_NULL = "Bitmap is null";
    }

    public interface VkApiMethods {
        String NEWSFEED_GET = "newsfeed.get";
        String GET_DIALOGS = "messages.getDialogs";
        String GET_USER_BY_ID = "users.get";
        String EXECUTE = "execute";
        String GET_USERS = "return {\"items\" : API.users.get({\"user_ids\" : \"%s\",  \"fields\" : \"photo_50, photo_100\"})} ;";
        String MESSAGES_GET_HISTORY = "messages.getHistory";
        String VALUE_20 = "20";
        String PEER_ID = "peer_id";
        String MESSAGES_GET_CHAT = "messages.getChat";
        String FILTERS = "filters";
        String START_FROM = "start_from";
        String POST = "post";
        String MESSAGES_SEND = "messages.send";
        String MESSAGES_GET_LONG_POLL_SERVER = "messages.getLongPollServer";
        String MESSAGES_GET_LONG_POLL_HISTORY = "messages.getLongPollHistory";
        String ITEM_ID = "item_id";
        String LIKES_ADD = "likes.add";
        String LIKES_DELETE = "likes.delete";
        String PHOTOS = "photos";
        String PHOTOS_GET_BY_ID = "photos.getById";
        String EXTENDED = "extended";
    }

    public interface Parser {
        String UNREAD = "unread";
        String MESSAGE = "message";
        String ID = "id";
        String OWNER_ID = "owner_id";
        String ARTIST = "artist";
        String TITLE = "title";
        String LYRICS_ID = "lyrics_id";
        String ALBUM_ID = "album_id";
        String GENRE_ID = "genre_id";
        String TYPE_PHOTO = "photo";
        String TYPE_VIDEO = "video";
        String TYPE_AUDIO = "audio";
        String TYPE_DOC = "doc";
        String TYPE_POST = "wall";
        String TYPE_POSTED_PHOTO = "posted_photo";
        String TYPE_LINK = "link";
        String TYPE_WIKI_PAGE = "page";
        String TYPE = "type";
        String SIZE = "size";
        String EXT = "ext";
        String URL = "url";
        String ACCESS_KEY = "access_key";
        String PHOTO_100 = "photo_100";
        String PHOTO_130 = "photo_130";
        String DESCRIPTION = "description";
        String IMAGE_SRC = "image_src";
        String PREVIEW_PAGE = "preview_page";
        String USER_ID = "user_id";
        String DATE = "date";
        String READ_STATE = "read_state";
        String OUT = "out";
        String BODY = "body";
        String ATTACHMENTS = "attachments";
        String EMPTY_STRING = "";
        String FWD_MESSAGE = "fwd_messages";
        String HEIGHT = "height";
        String WIDTH = "width";
        String TEXT = "text";
        String PHOTO_75 = "photo_75";
        String PHOTO_604 = "photo_604";
        String PHOTO_807 = "photo_807";
        String PHOTO_1280 = "photo_1280";
        String PHOTO_2560 = "photo_2560";
        String LIKES = "likes";
        String COUNT = "count";
        String USER_LIKES = "user_likes";
        String COMMENTS = "comments";
        String TAGS = "tags";
        String CAN_COMMENT = "can_comment";
        String TO_ID = "to_id";
        String FROM_ID = "from_id";
        String REPLY_OWNER_ID = "reply_owner_id";
        String REPLY_POST_ID = "reply_post_id";
        String FRIENDS_ONLY = "friends_only";
        String CAN_POST = "can_post";
        String CAN_LIKE = "can_like";
        String CAN_PUBLISH = "can_publish";
        String REPOSTS = "reposts";
        String USER_REPOSTED = "user_reposted";
        String POST_TYPE = "post_type";
        String SIGNER_ID = "signer_id";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String PHOTO_50 = "photo_50";
        String RESPONSE = "response";
        String DURATION = "duration";
        String VIEWS = "views";
        String CAN_REPOST = "can_repost";
        String PHOTO_320 = "photo_320";
        String GROUP_ID = "group_id";
        String CREATOR_ID = "creator_id";
        String SOURCE = "source";
        String EDITOR_ID = "editor_id";
        String EDITED = "edited";
        String CREATED = "created";
        String CODE = "code";
        String ITEMS = "items";
        String ERROR = "error";
        String ERROR_MSG = "error_msg";
        String DEACTIVATED = "deactivated";
        String KEY = "key";
        String SERVER = "server";
        String TS = "ts";
        String PHOTO_800 = "photo_800";
        String GROUPS = "groups";
        String PROFILES = "profiles";
        String NEXT_FROM = "next_from";
        String COPY_HISTORY = "copy_history";
        String USERS = "users";
        String ADMIN_ID = "admin_id";
        String CHAT_ID = "chat_id";
        String FAILED = "failed";
        String UPDATES = "updates";
        String MESSAGES = "messages";
        String IS_ADMIN = "is_admin";
        String IS_MEMBER = "is_member";
        String SCREEN_NAME = "screen_name";
        String NAME = "name";
        String POST_ID = "post_id";
        String SOURCE_ID = "source_id";
        String FIRST_FRAME_130 = "first_frame_130";
        String FIRST_FRAME_320 = "first_frame_320";
        String FIRST_FRAME_800 = "first_frame_800";
        String PHOTO = "photo";
        String PRODUCT_ID = "product_id";
        String PHOTO_64 = "photo_64";
        String PHOTO_128 = "photo_128";
        String PHOTO_256 = "photo_256";
        String PHOTO_352 = "photo_352";
        String PHOTO_512 = "photo_512";
        String TYPE_STICKER = "sticker";
        String TYPE_GIFT = "gift";
        String THUMB_48 = "thumb_48";
        String THUMB_96 = "thumb_96";
        String THUMB_256 = "thumb_256";
        String SEX = "sex";
        String RELATION = "relation";
        String BDATE = "bdate";
        String BDATE_VISIBILITY = "bdate_visibility";
        String HOME_TOWN = "home_town";
        String COUNTRY = "country";
        String CITY = "city";
        String STATUS = "status";
        String PHONE = "phone";
        String ERROR_CODE = "error_code";
    }

    public interface URL_BUILDER {
        String VERSION = "v";
        String VERSION_VALUE = "5.68";
        String ACCESS_TOKEN = "access_token";
        String HTTPS = "https";
        String API_VK_COM = "api.vk.com";
        String METHOD = "method";
        String HTTPS_OAUTH_VK = "https://oauth.vk.com/blank.html#access_token=";
        String COUNT = "count";
        String OFFSET = "offset";
        String FIELDS = "fields";
        String PHOTO_50_PHOTO_100 = "photo_50, photo_100";
        String START_MESSAGE_ID = "start_message_id";
        String USER_HISTORY = "user_history";
        String CHAT_ID = "chat_id";
        String TS = "ts";
        String LONG_POLL_RESPONSE = "https://%s?act=a_check&key=%s&ts=%s&wait=25&mode=32&version=2";
        String POST = "POST";
    }

    public interface Values {
        int INT_FOR_CHAT_ID = 2000000000;
        int PERCENT_FROM_DISK_SIZE = 50;
        String STRING_VALUES_FOUR = "4";
        String STRING_VALUE_ONE = "1";
        int VALUE_40 = 40;
    }

    public interface TIME {
        String TIME_FORMAT_H_MM = "HH:mm";
        String TIME_FORMAT_DD_MMMM = "dd MMMM";
        String TIME_FORMAT_DD_MMMM_YYYY = "dd MMMM yyyy";
        String DATE_FORMAT_DD_MMMM_HH_MM = "dd MMMM HH:mm";
        String DATE_FORMAT_DD_MMMM_YYYY_HH_MM = "dd MMMM yyyy HH:mm";

        String EUROPE_MINSK = "Europe/Minsk";
        String JAN = "янв.";
        String FEB = "фев.";
        String MARCH = "март";
        String APR = "апр.";
        String MAY = "май";
        String JUN = "июн.";
        String JUL = "июл.";
        String AUG = "авг.";
        String SEM = "сен.";
        String OCT = "окт.";
        String NOV = "нояб.";
        String DEC = "дек.";
    }
}
