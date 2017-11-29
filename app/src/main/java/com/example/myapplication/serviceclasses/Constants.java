package com.example.myapplication.serviceclasses;

public final class Constants {

    public static final String PARSE_ERROR_TAG = "Parse Error";
    public static final String MY_TAG = "My TAG";
    public static final String AUTHORIZATION_URL = "https://oauth.vk.com/authorize?client_id=6218232&display=mobile&redirect_uri=https://oauth.vk.com/blank.html&scope=friends,photos,audio,video,pages,status,notes,messages,wall,groups,offline&response_type=token&v=5.68&state=123456";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String FWD_MESSAGE = "Forward mMessages";
    public static final String WALL_POST = "Wall post";
    public static final String ERROR = "Error";
    public static final String UNKNOWN_JSON_RESPONSE = "Unknown json response ";
    public static final String ERROR_CLOSING_INPUT_STREAM = "Error closing InputStream";
    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 1;
    public static final String LANGUAGE = "ru";

    public interface VkApiMethods {
        String ONLINE_FRIENDS = "friends.getOnline";
        String NEWSFEED_GET = "newsfeed.get";
        String GET_DIALOGS = "messages.getDialogs";
        String GET_USER_BY_ID = "users.get";
        String EXECUTE = "execute";
        String GET_USERS_START = "return {\"items\" : API.users.get({\"user_ids\" : \"";
        String GET_USERS_END = "\",  \"fields\" : \"photo_50, photo_100\"})} ;";
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
        String FWD_MESSAGE = "fwd_message";
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
        String PHOTO_640 = "photo_640";
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
        String USER_IDS = "user_ids";
        String FIELDS = "fields";
        String PHOTO_50_PHOTO_100 = "photo_50, photo_100";
    }

    public interface TIME {
        String TIME_FORMAT_H_MM = "h:mm";
        String TIME_FORMAT_DD_MMMM = "dd MMMM";
        String TIME_FORMAT_DD_MMMM_YYYY = "dd MMMM yyyy";
    }
}
